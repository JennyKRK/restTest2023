package project1.persistence;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import project1.model.District;
import project1.model.Person;

import java.util.Collection;

@Component("districtService")
public class DistrictService {

    DatabaseConnector connector;
    public DistrictService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<District> getAllDistricts(){
        String hql = "FROM District";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public District getDistrictById(long id){
        return connector.getSession().get(District.class,id);
    }

    public void addANewDistrict(District district){
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(district);
        transaction.commit();
    }

    public void deleteADistrict(long id){
        District district = getDistrictById(id);
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(district);
        transaction.commit();
    }

    public void updateADistrict(District district){
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(district);
        transaction.commit();
    }

    public Collection<Person> addAPersonToDistrict(District district, Person person){
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        district.addAPerson(person);
        session.merge(district);
        transaction.commit();
        return district.getPeople();
    }

    public void removeAPersonFromDistrict(District district, Person person){
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        district.removeAPerson(person);
        session.merge(district);
        transaction.commit();
    }

    public Collection<District> findDistricts(String name, String sortBy){
        String hql = "From District as district WHERE name LIKE :name";
        if (sortBy.equals("name")){
            hql += " ORDER BY name";
        }
        Query query = connector.getSession().createQuery(hql);
        query.setParameter("name","%" + name + "%");
        return query.list();
    }

//    public Collection<Meeting> findMeetings(String title, String description, Participant participant, String sortMode) {
//        String hql = "FROM Meeting as meeting WHERE title LIKE :title AND description LIKE :description ";
//        if (participant != null) {
//            hql += " AND :participant in elements(participants)";
//        }
//        if (sortMode.equals("title")) {
//            hql += " ORDER BY title";
//        }
//        Query query = this.session.createQuery(hql);
//        query.setParameter("title", "%" + title + "%").setParameter("description", "%" + description + "%");
//        if (participant != null) {
//            query.setParameter("participant", participant);
//        }
//        return query.list();
//    }
}
