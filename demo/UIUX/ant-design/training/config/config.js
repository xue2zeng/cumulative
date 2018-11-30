export default {
  plugins: [
    ["umi-plugin-react", {
      antd: true,
      dva: true
    }]
  ],
  singular: true,
  routes: [{
    path: "/",
    component: "../layout",
    routes: [{
      path: '/helloworld',
      component: 'helloworld'
    }, {
      path: "/dashboard",
      routes: [
        {path: "/dashboard/analysis", component: "Dashboard/Analysis"},
        {path: "/dashboard/monitor", component: "Dashboard/Monitor"},
        {path: "/dashboard/workplace", component: "Dashboard/Workplace"}
      ]
    },{
      path: "/puzzlecards",
      component: 'puzzlecards'
    },{
      path: "list",
      component: '../page/list'
    }]
  }]
  // proxy: {
  //   '/dev': {
  //     target: 'https://08ad1pao69.execute-api.us-east-1.amazonaws.com',
  //     changeOrigin: true,
  //   }
  // }
}