package controllers;

import java.util.List;

import javax.inject.Inject;

import java.time.LocalDate;
import models.CalendarModel;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class CalendarController extends Controller {

    private CalendarModel calenderModel;

    @Inject
    public CalendarController(CalendarModel calenderModel) {
        this.calenderModel = calenderModel;
    }

    static class ScheduleMeeting {
        public int start_time;
        public int end_time;
        public List<String> employees;
    }

    static class FreeSlotsOfDay {
        public String employee1;
        public String employee2;
        public int date;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result scheduleMeeting(String invite) {
        ScheduleMeeting body = Json.fromJson(request().body().asJson(), ScheduleMeeting.class);
        return ok(this.calenderModel.scheduleMeeting(invite, body.employees, body.start_time, body.end_time));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result findFreeSlots() {
        FreeSlotsOfDay body = Json.fromJson(request().body().asJson(), FreeSlotsOfDay.class);
        return ok(Json.toJson(this.calenderModel.findFreeSlots(body.employee1, body.employee2, body.date,
                body.date + 24 * 60 * 60 * 1000)));
    }

    public Result multipleMeetingsSameTime(String meeting_id) {
        return ok(Json.toJson(this.calenderModel.multipleMeetingsSameTime(meeting_id)));
    }

}
