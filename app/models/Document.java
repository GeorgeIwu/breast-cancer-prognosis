package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @startuml

 * car --|> wheel

 * @enduml
 * Created by Dare on 20/05/2016.
 * <p>
 * I annotated Some of the Variables in case We want to create a table and use ebean in our database
 */
@Entity
public class Document extends Model{
    private static final SimpleDateFormat df= new SimpleDateFormat("dd MMM yyyy");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    public String name;

    public String filePath;

    public Date date = new Date();
    public String user;

    @Enumerated(value = EnumType.STRING)
    public Status status =Status.UPLOADED;

    public String purpose;

    @OneToOne
    public DocumentRangeBreast documentRangeBreast;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Patient> patients;

    public static Finder<Long, Document> find = new Finder<Long, Document>(Long.class, Document.class);

    public String formattedDate(){
        return df.format(date);
    }
    @Override
    public String toString() {
        return "Document{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                ", date=" + date +
                ", user='" + user + '\'' +
                ", status=" + status +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
