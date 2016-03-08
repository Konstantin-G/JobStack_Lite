package cz.garkusha.jobstack_lite.model;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 *@author Konstantin Garkusha
 *
 * */

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "positions")
public class PositionListWrapper {
    private List<Position> positions;

    @XmlElement(name = "position")
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
 }

