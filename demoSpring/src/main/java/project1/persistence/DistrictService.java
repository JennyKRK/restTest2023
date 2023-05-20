package project1.persistence;


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
}
