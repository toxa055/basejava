package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class Organization {
    private final Link homepage;
    private final List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this.homepage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!homepage.equals(that.homepage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homepage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homepage=" + homepage +
                ", positions=" + positions +
                '}';
    }
}
