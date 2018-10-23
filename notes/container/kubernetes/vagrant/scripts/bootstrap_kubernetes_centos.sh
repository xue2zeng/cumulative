#!/bin/bash

set -e

echo "############ Set SELinux in permissive mode (effectively disabling it)"
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

echo "############ Close swap"
swapoff -a
sed -i 's/.*swap.*/#&/' /etc/fstab

echo "############ Close firewalld"
systemctl stop firewalld
systemctl disable firewalld

echo "############ Set iptables routed"
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
vm.swappiness = 0
EOF
iptables -P FORWARD ACCEPT

sysctl --system

echo "############ Set enable ipvs"
modprobe ip_vs
modprobe ip_vs_rr
modprobe ip_vs_wrr
modprobe ip_vs_sh
modprobe nf_conntrack_ipv4
modprobe br_netfilter
lsmod | grep ip_vs

cat >>/etc/modules-load.d/k8s-ipvs.conf<<EOF
ip_vs
ip_vs_rr
ip_vs_wrr
ip_vs_sh
nf_conntrack_ipv4
br_netfilter
EOF

echo "############ Set hosts"
cat >>/etc/hosts<<EOF
192.168.61.10   node1
192.168.61.11   node2
192.168.61.12   node3
192.168.61.13   node4
192.168.61.14   node5
192.168.61.15   node6
192.168.61.16   node7
EOF

echo "############ Set yum repository"
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

echo "############ Linux kernel upgrade"
# 导入ELRepo软件仓库的公共秘钥
rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org
# 安装ELRepo软件仓库的yum源
rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm
yum --enablerepo=elrepo-kernel install  kernel-ml-devel kernel-ml -y
# 修改内核启动顺序，默认启动的顺序应该为1,升级以后内核是往前面插入为0
grub2-set-default 0

echo "############ Synchronised system time"
# 调整系统 TimeZone
timedatectl set-timezone Asia/Shanghai
# 将当前的 UTC 时间写入硬件时钟
timedatectl set-local-rtc 0
# 重启依赖于系统时间的服务
systemctl restart rsyslog 
systemctl restart crond
yum install -y ntp
ntpdate cn.pool.ntp.org

echo "############ Install docker kubelet kubeadm ipvsadm"
yum install -y yum-utils device-mapper-persistent-data lvm2
yum install -y docker-ce ipvsadm kubelet kubeadm kubectl kubernetes-cni cri-tools --disableexcludes=kubernetes

echo "############ Start docker kubelet"
systemctl enable docker
systemctl enable kubelet.service
systemctl start docker
systemctl start kubelet

