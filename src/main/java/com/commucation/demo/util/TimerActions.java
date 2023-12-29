//package com.commucation.demo.util;
//
//import com.commucation.demo.common.config.WebSocketServer;
//import com.commucation.demo.entity.*;
//import com.commucation.demo.mapper.TransportMapper;
//import com.commucation.demo.mapper.UserMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//@Slf4j
//@Component
//@EnableScheduling
//public class TimerActions {
//
//    @Resource
//    UserMapper userMapper;
//    @Resource
//    TransportMapper transportMapper;
//
//    // 上锁
//    private static final Lock lock = new ReentrantLock(true);
//
//    //课表时间映射
//    private final HashMap<String, String> timeMapping = new HashMap<String, String>() {
//        {
//            put("周一第1,2节", "1");
//            put("周一第3,4节", "2");
//            put("周一第7,8节", "3");
//            put("周一第9,10节", "4");
//            put("周一第11,12节", "5");
//            put("周一第11,12,13节", "5");
//            put("周二第1,2节", "6");
//            put("周二第3,4节", "7");
//            put("周二第7,8节", "8");
//            put("周二第9,10节", "9");
//            put("周二第11,12节", "10");
//            put("周二第11,12,13节", "10");
//            put("周三第1,2节", "11");
//            put("周三第3,4节", "12");
//            put("周三第7,8节", "13");
//            put("周三第9,10节", "14");
//            put("周三第11,12节", "15");
//            put("周三第11,12,13节", "15");
//            put("周四第1,2节", "16");
//            put("周四第3,4节", "17");
//            put("周四第7,8节", "18");
//            put("周四第9,10节", "19");
//            put("周四第11,12节", "20");
//            put("周四第11,12,13节", "20");
//            put("周五第1,2节", "21");
//            put("周五第3,4节", "22");
//            put("周五第7,8节", "23");
//            put("周五第9,10节", "24");
//            put("周五第11,12节", "25");
//            put("周五第11,12,13节", "25");
//            put("周六第1,2节", "26");
//            put("周六第3,4节", "27");
//            put("周六第7,8节", "28");
//            put("周六第9,10节", "29");
//            put("周六第11,12节", "30");
//            put("周六第11,12,13节", "30");
//            put("周日第1,2节", "31");
//            put("周日第3,4节", "32");
//            put("周日第7,8节", "33");
//            put("周日第9,10节", "34");
//            put("周日第11,12节", "35");
//            put("周日第11,12,13节", "35");
//        }
//    };
//
//    // */5 * * * * ?
//
//    @Scheduled(cron = "0 0 0 * * ?")
//    private void XXMCTimer() {
//        if (lock.tryLock()) {
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "select * from xxmc";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                while (result.next()) {
//                    String dqxn = result.getString("dqxn");
//                    String dqxq = result.getString("dqxq");
//
//                    transportMapper.updateStrip(dqxn, dqxq);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//
//    @Scheduled(cron = "*/30 * * * * ?")
//    public void teacherTimer() {
//        if (lock.tryLock()) {
////            log.info("This is teacherTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "select count(*) from yhb";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                result.next();
//                int teacherSum = result.getInt("count(*)");
//                int teacherNum = transportMapper.findTeacherNum();
//
//                if (teacherSum != teacherNum) {
//                    sql = "select * from yhb";
//                    result = state.executeQuery(sql);
//
//                    int num = 0;
//                    while (result.next()) {
//                        String yhm = result.getString("yhm");
//                        if (yhm != null && transportMapper.findTeacherByYHM(yhm) == null) {
//                            String xm = result.getString("xm"); //姓名
//                            String szdw = result.getString("szdw"); //所在单位
//
//                            Statement statement = conn.createStatement();
//
//                            String sql1 = "select * from jsxxb where zgh = '" + yhm + "'";
//                            ResultSet resultSet = statement.executeQuery(sql1);
//
//                            String lxdh = "";
//                            String sfzh;
//                            if (resultSet.next()) {
//                                lxdh = resultSet.getString("lxdh"); //联系电话
//                                sfzh = resultSet.getString("sfzh"); //身份证号
//                            } else {
//                                sfzh = result.getString("sfzh");
//                            }
//
//                            String newPassword = (new SimpleHash("md5",(sfzh == null || sfzh.equals(""))? yhm : sfzh.substring(sfzh.length() - 6), yhm, 14).toString());
//                            transportMapper.insertUserInfo(2, xm, LocalDateTime.now(), yhm, newPassword, lxdh);
////                            log.info("save user info success(username:[{}])", xm);
//                            transportMapper.insertTeacherInfo(yhm, szdw);
////                            log.info("save teacher info success(teacher_no[{}])", yhm);
//                            teacherNum++;
//                            transportMapper.updateTeacherNum(teacherNum);
//
//                            resultSet.close();
//                            statement.close();
//                        }
//                    }
//                    result.close();;
//                    state.close();
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//
//    @Scheduled(cron = "*/50 * * * * ?")
//    public void studentTimer() {
//        if (lock.tryLock()) {
////            log.info("This is studentTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "select count(*) from xsjbxxb";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                result.next();
//                int studentSum = result.getInt("count(*)");
//                int studentNum = transportMapper.findStudentNum();
//                if (studentSum != studentNum) {
//                    Statement statement = conn.createStatement();
//                    sql = "select * from xsjbxxb where xjzt = '有'";
//                    ResultSet resultSet = statement.executeQuery(sql);
//                    while (resultSet.next()) {
//                        String xh = resultSet.getString("xh");
//
//                        if (xh != null && transportMapper.findStudentByXH(xh) == null) {
//                            String xm = resultSet.getString("xm");
//                            String xy = resultSet.getString("xy");
//                            String zymc = resultSet.getString("zymc");
//                            String sfzh = resultSet.getString("sfzh");
//                            String xzb = resultSet.getString("xzb");
//                            String dqszj = resultSet.getString("dqszj");
//
//                            String newPassword = (new SimpleHash("md5", sfzh.substring(12), xh, 14).toString());
//
//                            transportMapper.insertUserInfo(3, xm, LocalDateTime.now(), xh, newPassword, "");
////                            log.info("save user info success(username:[{}])", xm);
//                            transportMapper.insertStudentInfo(xh, xzb, xy, zymc, dqszj);
////                            log.info("save student info success(student_no:[{}])", xh);
//                            studentNum++;
//                            transportMapper.updateStudentNum(studentNum);
//                        }
//                    }
//                    resultSet.close();
//                    statement.close();
//                }
//                result.next();
//                state.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//           // 0 0/30 * * * ?
//    @Scheduled(cron = "*/40 * * * * ?")
//    public void courseTimer() {
//        if (lock.tryLock()) {
////            log.info("This is courseTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "select count(*) from (select DISTINCT XKKH from JXRWBVIEW)";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                result.next();
//                int courseSum = result.getInt("count(*)");
//                int courseNum = transportMapper.findJXRWBVIEWNum();
//
//                if (courseSum != courseNum) {
//                    Statement statement = conn.createStatement();
//                    sql = "select * from jxrwbview";
//                    ResultSet resultSet = statement.executeQuery(sql);
//
//                    while (resultSet.next()) {
//                        String xkkh = resultSet.getString("xkkh");
//                        String teacher_id = resultSet.getString("jszgh");
//
//                        if (userMapper.findByUsername(teacher_id) == null) {
//                            log.warn("教师不存在,创建课程失败,选课课号:[{}]", xkkh);
//                        } else if (xkkh != null && transportMapper.findCourseByXKKH(xkkh) == null) {
//                            String course_name = resultSet.getString("kcmc");
//                            String teacher_name = resultSet.getString("jsxm");
//                            String class_add = resultSet.getString("skdd");
//
//                            // 处理上课时间的映射逻辑(将 周*第*,*节 映射为map中的数字)
//                            String class_time = "";
//                            String sksj = resultSet.getString("sksj");
//                            if (sksj != null) {
//                                String[] tempStrings = sksj.split(";");
//                                for (int i = 0; i < tempStrings.length; i++) {
//                                    String temp = tempStrings[i].substring(0, tempStrings[i].indexOf('{'));
//                                    temp = tempStrings[i].replace(temp, timeMapping.get(temp) == null ? "" : timeMapping.get(temp));
//                                    class_time += ((i == 0 ? "" : ";") + temp);
//                                }
//                            }
//
//                            transportMapper.insertCourseInfo(xkkh, teacher_id, teacher_name, course_name, class_time, class_add);
////                            log.info("save course info success(xkkh:[{}])", xkkh);
//
//                            String group_name = teacher_name + "的" + course_name + "群";
//                            Integer course_id = transportMapper.findCourseIdByXKKH(xkkh);
//                            transportMapper.createGroup(group_name, course_id);
//                            Integer group_id = transportMapper.findGroupIdByCourseId(course_id);
//                            transportMapper.addGroupIdToCourseInfo(group_id, course_id);
////                            log.info("create group success(group_id:[{}])", group_id);
//                            transportMapper.insertUserGroupInfo(userMapper.findByUsername(teacher_id).getId(), group_id);
////                            log.info("save user_group info success(user_id:[{}]), group_id:[{}]", userMapper.findByUsername(teacher_id).getId(), group_id);
//                            courseNum++;
//                            transportMapper.updateJXRWBVIEWNum(courseNum);
//                        }
//                    }
//                    resultSet.close();
//                    statement.close();
//                }
//                result.close();;
//                state.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
////    "0 0/40 * * * ?"
//
//    @Scheduled(cron = "0 0/50 * * * ?")
//    public void stuCourseTimer() {
//        if (lock.tryLock()) {
////            log.info("This is stuCourseTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "select * from xsxkb";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                while (result.next()) {
//                    String xh = result.getString("xh");
//                    String xkkh = result.getString("xkkh");
//
//                    if (xh != null && xkkh != null && transportMapper.findStuCourseByXHAndXKKH(xh, xkkh) == null) {
//                        String xksj = result.getString("xksj");
//                        if (userMapper.findByUsername(xh) == null) {
////                            log.warn("save stu_course info fail(xh:[{}],xkkh:[{}])(学号不存在)", xh, xkkh);
//                            continue;
//                        } else if (transportMapper.findCourseByXKKH(xkkh) == null) {
////                            log.warn("save stu_course info fail(xh:[{}],xkkh:[{}])(课程号不存在)", xh, xkkh);
//                            continue;
//                        }
//                        Integer stu_id = userMapper.findStudentInfo(userMapper.findByUsername(xh).getId()).getId();
//                        Integer course_id = transportMapper.findCourseIdByXKKH(xkkh);
//
//                        transportMapper.insertStuCourseInfo(stu_id, course_id, LocalDateTime.parse(xksj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
////                        log.info("save stu_course info success(stu_id:[{}],course_id:[{}])", stu_id, course_id);
//                        transportMapper.insertUserGroupInfo(userMapper.findByUsername(xh).getId(), transportMapper.findGroupIdByCourseId(course_id));
////                        log.info("save stu_group info success(user_id:[{}], group_id:[{}])", userMapper.findByUsername(xh), transportMapper.findGroupIdByCourseId(course_id));
//                    }
//                }
//
//                result.close();
//                state.close();
//
//                Statement statement = conn.createStatement();
//                sql = "select * from xsxkb_trigger";
//                ResultSet results = statement.executeQuery(sql);
//
//                while (results.next()) {
//                    String xh = results.getString("xh");
//                    String xkkh = results.getString("xkkh");
//                    LocalDateTime sj = LocalDateTime.parse(results.getString("sj"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//                    List<StuCourse> stuCourseList = transportMapper.findStuCourseByXHAndXKKHs(xh, xkkh);
//                    if (stuCourseList != null) {
//                        for (StuCourse stuCourse : stuCourseList) {
//                            if (stuCourse.getXksj().isBefore(sj)) {
//                                transportMapper.deleteStuCourseById(stuCourse.getId());
////                                log.info("delete stu_course info success(stu_id:[{}],course_id:[{}])(Withdraw)", stuCourse.getStu_id(), stuCourse.getCourse_id());
//                            }
//                        }
//                    }
//                }
//                results.close();
//                statement.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0/59 * * * ?")
//    public void GradeTimer() throws SQLException, ClassNotFoundException {
//        if (lock.tryLock()) {
////            log.info("This is GradeTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet resultJXRWBVIEW;
//                Statement state = null;
//                String sql = "select * from jxrwbview";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                resultJXRWBVIEW = state.executeQuery(sql);
//
//                while (resultJXRWBVIEW.next()) {
//                    String xkkh = resultJXRWBVIEW.getString("xkkh");
//                    String lrsz = resultJXRWBVIEW.getString("lrsz");
//
//                    if (xkkh == null || lrsz == null) {
//                        log.info("选课课号或录入设置为空");
//                    } else {
//                        Course course = transportMapper.findCourseByXKKH(xkkh);
//                        if (course != null && (course.getLrsz() == null || !course.getLrsz().equals(lrsz))) {
//                            transportMapper.changeGradeStatus(course.getId(), lrsz);
////                            log.info("change grade_status success(course_id:[{}],status:[{}])", course.getId(), lrsz);
//
//                            if (lrsz.equals("提交")) {
//                                Statement statement = conn.createStatement();
//                                String searchSql = "select * from CJB where xkkh = '" + xkkh + "'";
//                                ResultSet resultCJB = statement.executeQuery(searchSql);
//                                while (resultCJB.next()) {
//                                    String xh = resultCJB.getString("xh");
//                                    String cj = resultCJB.getString("cj");
//                                    String jd = resultCJB.getString("jd");
//
//                                    if (transportMapper.findStuCourseByXHAndXKKH(xh, xkkh) != null) {
//                                        transportMapper.changeGrade(xh, xkkh, cj, jd);
////                                        log.info("change grade success(xkkh:[{}],xh:[{}])", xkkh, xh);
//                                        String content = "课程:" + course.getCourse_name() + ",成绩:" + (cj == null ? "无记录" : cj) + ",绩点:" + (jd == null ? "无记录" : jd);
//                                        transportMapper.insertSystemNote(content, LocalDateTime.now(), course.getTeacher_name(), userMapper.findByUsername(xh).getId(), "", "成绩说明");
//                                        if (userMapper.findByUsername(xh).getIs_online() == 1) {
//                                            SystemNote systemNote = new SystemNote();
//                                            systemNote.setId(transportMapper.findMaxIdOfSystemNote());
//                                            systemNote.setIs_read(0);
//                                            systemNote.setContent(content);
//                                            systemNote.setGmt_create(LocalDateTime.now());
//                                            systemNote.setSender(course.getTeacher_name());
//                                            WebSocketServer.sendNote(systemNote);
//                                        }
//                                    }
//                                }
//                                resultCJB.close();
//                                statement.close();
//                            }
//                        }
//                    }
//                }
//                resultJXRWBVIEW.close();
//                state.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0/30 * * * ?")
//    //设置每5秒执行一下
//    public void systemNoteTimer() throws SQLException, ClassNotFoundException {
//        if (lock.tryLock()) {
////            log.info("This is systemNoteTimer，present clock：[{}]", LocalTime.now().toString());
//            try {
//                Connection conn;
//                ResultSet result;
//                Statement state = null;
//                String sql = "SELECT * FROM jwggfbb where fbsj > (select  to_char(ADD_MONTHS(sysdate, -12),'yyyy-mm-dd')  from  dual)";
//
//                conn = Conn.getConnection();
//                if (conn != null){
//                    state = conn.createStatement();
//                }
//                assert state != null;
//                result = state.executeQuery(sql);
//
//                while (result.next()) {
//                    String ggsm = result.getString("ggsm");
//                    String fbsj = result.getString("fbsj");
//
//                    if (ggsm != null && fbsj != null && transportMapper.findSystemNoteByContentAndTime(ggsm, LocalDateTime.parse(fbsj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) == null) {
//                        String ggbt = result.getString("ggbt");
//                        String fbdw = result.getString("fbdw");
//                        String xh = result.getString("xh");
//                        String dir = "";
//
//
//                        Statement statement = conn.createStatement();
//                        sql = "select * from jwggfbwjb where xh = " + xh;
//                        ResultSet resultSet = statement.executeQuery(sql);
//                        if (resultSet.next()) {
//                            dir = resultSet.getString("fullpath");
//                        }
//
//                        List<User> userList = userMapper.findAll();
//                        if (userList != null) {
//                            for (User user : userList) {
//                                transportMapper.insertSystemNote(ggsm, LocalDateTime.parse(fbsj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), fbdw, user.getId(), dir, ggbt);
//                            }
////                            log.info("save system note success");
//                        }
//
//                        SystemNote systemNote = new SystemNote();
//                        systemNote.setDir(dir);
//                        systemNote.setTitle(ggbt);
//                        systemNote.setContent(ggsm);
//                        systemNote.setGmt_create(LocalDateTime.parse(fbsj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                        systemNote.setSender(fbdw);
//                        systemNote.setIs_read(0);
//                        WebSocketServer.sendNote(systemNote);
//
//                        resultSet.close();
//                        statement.close();
//                    }
//                }
//                result.close();
//                state.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("Exception**********", e);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
//}