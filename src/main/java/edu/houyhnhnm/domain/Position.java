/*
 * Created by m1s on 8/9/14.
 */

package edu.houyhnhnm.domain;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: Mihai
 */
@Entity
public class Position {

    private Long id;

    private String name;

    private Point location;

    public Position() {
    }

    public Position(String name, Point location) {
        this.name = name;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Type(type="org.hibernate.spatial.GeometryType")
    public Point   getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Position{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
