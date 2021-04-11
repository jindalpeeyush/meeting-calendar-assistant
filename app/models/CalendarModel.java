package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import play.libs.Json;

import javax.inject.Inject;
import org.apache.commons.lang.RandomStringUtils;

import dao.CalendarDao;
import vo.CalendarVo;

public class CalendarModel {

    CalendarDao calendarDao;

    @Inject
    public CalendarModel(CalendarDao calendarDao) {
        this.calendarDao = calendarDao;
    }

    public String scheduleMeeting(String invite, List<String> employees, int start_time, int end_time) {
        String meeting_id = RandomStringUtils.random(10, true, true);
        CalendarVo vo = new CalendarVo();
        vo.meeting_id = meeting_id;
        vo.start_at = start_time;
        vo.end_at = end_time;
        vo.employee_id = invite;
        this.calendarDao.insertData(vo);
        for (String employee : employees) {
            vo = new CalendarVo();
            vo.meeting_id = meeting_id;
            vo.start_at = start_time;
            vo.end_at = end_time;
            vo.employee_id = employee;
            this.calendarDao.insertData(vo);
        }
        return meeting_id;
    }

    static class TimeSlots {
        int start_time;
        int end_time;
    }

    public List<TimeSlots> findFreeSlots(String employee1, String employee2, int start_time, int end_time) {
        List<CalendarVo> employee1Data = this.calendarDao.getEmployeeMeetings(employee1, start_time, end_time);
        List<CalendarVo> employee2Data = this.calendarDao.getEmployeeMeetings(employee2, start_time, end_time);
        List<TimeSlots> timeSlots = new LinkedList<>();
        List<CalendarVo> joined = new LinkedList<>();
        joined.addAll(employee1Data);
        joined.addAll(employee2Data);
        for (CalendarVo employee : joined) {
            TimeSlots slot = new TimeSlots();
            slot.start_time = employee.start_at;
            slot.end_time = employee.end_at;
            timeSlots.add(slot);
        }
        Collections.sort(timeSlots, new Comparator<TimeSlots>() {
            @Override
            public int compare(TimeSlots o1, TimeSlots o2) {
                return o1.start_time - o2.start_time;
            }
        });
        List<TimeSlots> freeSlots = new LinkedList<>();
        int i = 0;
        for (int start = start_time; start < end_time;) {
            if (i < timeSlots.size()) {
                if (timeSlots.get(i).start_time >= start + (30 * 60 * 1000)) {
                    for (int slotTime = start; slotTime < timeSlots.get(i).start_time
                            && slotTime + (30 * 60 * 1000) <= timeSlots.get(i).start_time;) {
                        TimeSlots slot = new TimeSlots();
                        slot.start_time = slotTime;
                        slotTime += (30 * 60 * 1000);
                        slot.end_time = slotTime;
                        freeSlots.add(slot);
                    }
                } else {
                    start = timeSlots.get(i).end_time;
                    i++;
                }
            } else {
                for (int slotTime = start; slotTime < end_time;) {
                    TimeSlots slot = new TimeSlots();
                    slot.start_time = slotTime;
                    slotTime += (30 * 60 * 1000);
                    slot.end_time = slotTime;
                    freeSlots.add(slot);
                }
            }
        }
        return freeSlots;
    }

    public List<String> multipleMeetingsSameTime(String meeting) {
        List<CalendarVo> meetings = this.calendarDao.getMeetingEmployee(meeting);
        List<String> employees = new LinkedList<>();
        for (CalendarVo vo : meetings) {
            List<CalendarVo> employeeMeetings = this.calendarDao.getEmployeeMeetings(vo.employee_id, vo.start_at,
                    vo.end_at);
            System.out.println(employeeMeetings);
            if (employeeMeetings.size() > 1) {
                employees.add(vo.employee_id);
            }
        }
        return employees;
    }
}
