package com.commucation.demo.controller;

import com.commucation.demo.common.config.SpellComparator;
import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.*;
import com.commucation.demo.mapper.RelationMapper;
import com.commucation.demo.mapper.UserMapper;
import com.commucation.demo.util.PictureModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/relation")
public class RelationController {
    @Resource
    private RelationMapper relationMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/findMyGroups/{identity_no}")
    public Result findMyGroups(@PathVariable(name = "identity_no") String identity_no){
        if (identity_no == null) {
            return Result.fail("用户名为空");
        }
        try {
            if (userMapper.findByUsername(identity_no).getRole_id() == 2) {
                List<Group> groups = relationMapper.findGroupsByTeacherNo(identity_no);
                if (groups != null) {
                    for (Group item : groups) {
                        item.setHead(new PictureModel(item.getHead_dir()).getPic());
                    }
                }
                return Result.success(groups);
            } else if (userMapper.findByUsername(identity_no).getRole_id() == 3) {
                Student studentInfo = relationMapper.findMyFinishedCourse(identity_no);
                List<Group> groups = relationMapper.findGroups(identity_no);
                List<Group> result = new ArrayList<Group>();
                if (groups != null) {
                    if (studentInfo != null) {
                        String[] finishedCourse = studentInfo.getFinished_course().split(";");
                        for (Group group : groups) {
                            boolean flag = false;
                            for (String s : finishedCourse) {
                                if (group.getCourse_id().toString().equals(s)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                result.add(group);
                            }
                        }
                    } else {
                        result = groups;
                    }

                    for (Group item : result) {
                        item.setHead(new PictureModel(item.getHead_dir()).getPic());
                    }
                }
                return Result.success(result);
            }
            return Result.fail("查询失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @PostMapping("/findGroupMember")
    public Result findGroupMemberByGroupId(Group group){
        if (group == null || group.getId() == null) {
            return Result.fail("群id为空");
        }
        try {
            Group groupInfo = new Group();

            List<User> list = userMapper.findUserByGroupId(group.getId());
            if (list != null) {
                for (User user : list) {
                    user.setHead(new PictureModel(user.getHeadDir()).getPic());
                }

                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        try {
                            // 取得比较对象的汉字编码，并将其转换成字符串
                            String s1 = new String(o1.getUsername().getBytes("GB2312"), "ISO-8859-1");
                            String s2 = new String(o2.getUsername().getBytes("GB2312"), "ISO-8859-1");
                            // 运用String类的 compareTo（）方法对两对象进行比较
                            return s1.compareTo(s2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                Collections.reverse(list);
            }
            groupInfo.setGroupMember(list);
            return Result.success(groupInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @PostMapping("/searchCourseByTeacherName")
    public Result searchCourseByTeacherName(@RequestBody Course course) {
        if (course == null || course.getTeacher_name() == null) {
            return Result.fail("教师姓名为空");
        }
        try {
            List<Course> courses = relationMapper.searchCourseByTeacherName(course.getTeacher_name());
            return Result.success(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @PostMapping("/searchCourseByCourseName")
    private Result searchCourseByCourseName(@RequestBody Course course) {
        if (course == null || course.getCourse_name() == null) {
            return Result.fail("课程名为空");
        }
        try {
            List<Course> courses = relationMapper.searchCourseByCourseName(course.getCourse_name());
            return Result.success(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @PostMapping("/searchDialog")
    private Result searchDialog(@RequestBody SearchDialogBody searchDialogBody) {
        if (searchDialogBody.getName() == null || searchDialogBody.getIdentity_no() ==null) {
            return Result.fail("查询内容或账号为空");
        }
        try {
            List<SearchDialogResult> searchDialogResultList = relationMapper
                    .searchDialog(searchDialogBody.getName(), searchDialogBody.getIdentity_no());
            if (searchDialogResultList != null) {
                for (SearchDialogResult item : searchDialogResultList) {
                    item.setHead(new PictureModel(item.getHeadDir()).getPic());
                }
            }
            return Result.success(searchDialogResultList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询出错");
        }
    }
}
