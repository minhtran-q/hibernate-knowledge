package vn.hibernateknowledge.hibernateorm.infrastructure.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "data")
public class DataEntity implements Serializable{

    private static final long serialVersionUID = -3072713753777744412L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_sequence_generator")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "data_sequence_generator", sequenceName = "data_sequence")
    @Column(name = "data_id")
    private Long id;
    
    private String data1;
    
    private String data2;
    
    private String data3;
    
    private String data4;

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }
}