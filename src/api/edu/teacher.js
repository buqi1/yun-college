import request from '@/utils/request'

export default {
    getTeacherListPage(page,limit,teacherQuery) {
        return request({
            url: `/eduservice/teacher/${page}/${limit}`,
            method: 'post',
            data: teacherQuery
        })
    },

    //删除讲师
    deleteTeacherId(id){
        return request({
            url: `/eduservice/teacher/delete/${id}`,
            method: 'delete'
        })
    },

    //添加讲师
    addTeacher(eduTeacher){
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: eduTeacher
        })
    },
    //修改讲师
    getTeacherId(id){
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get',
        })
    },

    updateTeacher(eduTeacher){
        return request({
            url: `/eduservice/teacher/upTeacher`,
            method: 'post',
            data: eduTeacher
        })
    }
}