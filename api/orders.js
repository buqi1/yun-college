import request from '@/utils/request'

export default {
    //生成订单
  createOrders(courseId) {
    return request({
      url: '/eduorder/order/saveOrder/'+courseId,
      method: 'post'
    })
  },
  //根据订单id查询订单信息
  getOrdersInfo(id) {
    return request({
      url: '/eduorder/order/getOrderInfo/'+id,
      method: 'get'
    })
  },
  //生成二维码的方法
  createNatvie(orderNo) {
    return request({
      url: '/eduorder/paylog/saveWxCode/'+orderNo,
      method: 'post'
    })
  },

  //查询订单状态的方法
  //生成二维码的方法
  queryPayStatus(orderNo) {
    return request({
      url: '/eduorder/paylog/getPayStatus/'+orderNo,
      method: 'get'
    })
  }
}