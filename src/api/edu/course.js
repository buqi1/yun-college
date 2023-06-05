import request from '@/utils/request'

export default {
    //添加课程信息
    addCourse(courseVO) {
        return request({
            url: `/eduservice/course/addCourse`,
            method: 'post',
            data: courseVO
        })
    },
    //查询所有讲师
    findAll(){
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get'
        })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(id) {
        return request({
            url: '/eduservice/course/getCourseVO/'+id,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseVO(courseVO) {
        return request({
            url: '/eduservice/course/updateCourseVO',
            method: 'post',
            data: courseVO
          })
    },
     //课程确认信息显示
     getPublihCourseInfo(id) {
        return request({
            url: '/eduservice/course/getCoursePublish/'+id,
            method: 'get'
          })
    },
    //课程最终发布(状态的变化)
    publihCourse(id) {
        return request({
            url: '/eduservice/course/publishCourse/'+id,
            method: 'post'
          })
    },
    //课程最终发布 
    getListCourse() {
        return request({
            url: '/eduservice/course/getList',
            method: 'get'
          })
    }
}