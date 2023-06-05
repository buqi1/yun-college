import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: video
          })
    },
    
    //删除小节
    deleteVideo(id) {
        return request({
            url: '/eduservice/video/'+id,
            method: 'delete'
          })
    },
    //修改小节
    updateVideo(video) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data:video
          })
    },
    //根据id查询小节
    getVideo(chapterId) {
        return request({
            url: '/eduservice/video/getVideo/'+chapterId,
            method: 'get'
          })
    },
    //删除视频
    deleteVideoById(id) {
        return request({
            url: '/eduvod/deleteVideo/'+id,
            method: 'delete'
          })
    },
}