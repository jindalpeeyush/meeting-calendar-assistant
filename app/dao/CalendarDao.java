package dao;

import java.util.List;
import java.util.LinkedList;

import javax.inject.Inject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import play.libs.Json;
import org.mongodb.morphia.AdvancedDatastore;

import exceptions.ObjectNotFoundException;

import services.db.Mongo;
import vo.CalendarVo;

public class CalendarDao {

	private AdvancedDatastore dataStore;
	protected Mongo mongodb;

	@Inject
	public CalendarDao(Mongo mongo) {
		this.mongodb = mongo;
		this.dataStore = mongo.datastore;
	}

	public CalendarVo insertData(CalendarVo vo) {
		this.dataStore.insert(vo);
		return vo;
	}

	public List<CalendarVo> getEmployeeMeetings(String employee, int start_time, int end_time) {
		List<CalendarVo> vos = this.dataStore.find(CalendarVo.class).filter("employee_id =", employee)
				.filter(" start_at >=", start_time).filter(" start_at <", end_time).asList();
		return vos;
	}

	public List<CalendarVo> getMeetingEmployee(String meeting_id) {
		List<CalendarVo> vos = this.dataStore.find(CalendarVo.class).filter("meeting_id =", meeting_id).asList();
		return vos;
	}

	public AdvancedDatastore getDataStore() {
		return dataStore;
	}
}
