package project1.persistence;

import java.util.Collection;


import org.hibernate.Session;
import org.hibernate.Transaction;
import project1.model.Person;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;


@Component("personService")
public class PersonService {

    DatabaseConnector connector;

    public PersonService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Person> getAllPeople(){
        String hql = "FROM Person";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Person getAPersonById(long id){
        return (Person) connector.getSession().get(Person.class, id);
    }

    public void addAPerson(Person person){

        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();
    }

    public void deleteAPerson(long id){
        Session session = connector.getSession();
        Person person = getAPersonById(id);
        Transaction transaction = session.beginTransaction();
        session.delete(person);
        transaction.commit();
    }

    public void updateAPerson(Person person){
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(person);
        transaction.commit();
    }
}
