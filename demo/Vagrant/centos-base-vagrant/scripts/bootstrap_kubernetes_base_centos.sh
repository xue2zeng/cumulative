#!/bin/bash

set -e

echo "Set SELinux in permissive mode (effectively disabling it)"
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

echo "Close swap"
swapoff -a
sed -i 's/.*swap.*/#&/' /etc/fstab

echo "Close firewalld"
systemctl stop firewalld
systemctl disable firewalld

echo "Set iptables routed"
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
vm.swappiness = 0
EOF

sysctl --system

echo "Set enable ipvs"
cat >>/etc/modules-load.d/k8s-ipvs.conf<<EOF
ip_vs
ip_vs_rr
ip_vs_wrr
ip_vs_sh
nf_conntrack_ipv4
br_netfilter
EOF

echo "Set hosts"
cat >>/etc/hosts<<EOF
192.168.61.11   node1
192.168.61.12   node2
192.168.61.13   node3
EOF

echo "Set yum repository"
yum install -y wget
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

cat >>/etc/yum.repos.d/kubernetes.repo<<EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
exclude=kube*
EOF

yum clean all -y && yum makecache -y

echo "Install docker kubelet kubeadm ipvsadm"
yum install -y yum-utils device-mapper-persistent-data lvm2
yum install -y docker-ce #kubelet kubeadm kubectl kubernetes-cni --disableexcludes=kubernetes ipvsadm

echo "Start docker kubelet"
systemctl enable docker
#systemctl enable kubelet.service
systemctl start docker
#systemctl start kubelet

