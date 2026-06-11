package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Room implements Serializable {

    // base on uml add serialVersionUID
    private static final long serialVersionUID = 1L;

    /**
     * All the different directions a Door can be in a Room.
     */
    public enum Direction {NORTH, SOUTH, EAST, WEST};

    // use Map to store doors，Key is direction (e.g. "North", "South")，Value is Door obj
    private Map<Direction, Door> myDoors;

    // see if the door is the way of exit
    private boolean isExit;

    /**
     * Constructor
     */
    public Room() {

        HashMap<Direction, Door> doorMap = new HashMap<>();
        
        // new database manager
        DatabaseManager db = new DatabaseManager();
        // choose questions randomly when new door create
        doorMap.put(Direction.NORTH, new Door(db.getRandomQuestion()));
        doorMap.put(Direction.SOUTH, new Door(db.getRandomQuestion()));
        doorMap.put(Direction.EAST, new Door(db.getRandomQuestion()));
        doorMap.put(Direction.WEST, new Door(db.getRandomQuestion()));
        // initialize Map
        myDoors = doorMap;
        // initialize the ROOM is not the exit
        isExit = false;
    }

    /**
     * set door
     */
    public void setDoor(Direction theDirection, Door theDoor) {
        myDoors.put(theDirection, theDoor);
    }

    /**
     * get door
     */
    public Door getDoor(Direction theDirection) {
        return myDoors.get(theDirection);
    }

    /**
     * return if the room is exit
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * set whether the room is exit
     */
    public void setExit(boolean theIsExit) {
        this.isExit = theIsExit;
    }

    /**
     * String Representation of Room.
     * @return String representation of a room.
     */
    @Override
    public String toString() {
        Door.DoorStatus doorStatus;
        StringBuilder sb = new StringBuilder();

        // Row 1: North Door
        sb.append("* ");
        doorStatus = getDoor(Direction.NORTH).getStatus();
        if(doorStatus == Door.DoorStatus.CLOSED){
            sb.append("-");
        }
        else if(doorStatus == Door.DoorStatus.OPEN){
            sb.append(" ");
        }
        else{
            sb.append("L");
        }
        sb.append(" *\n");

        // Row 2: West and East Doors
        doorStatus = getDoor(Direction.WEST).getStatus();
        if(doorStatus == Door.DoorStatus.CLOSED){
            sb.append("| ");
        }
        else if(doorStatus == Door.DoorStatus.OPEN){
            sb.append("  ");
        }
        else{
            sb.append("L ");
        }
        sb.append("R"); // 'R' for Room

        // East Doors
        doorStatus = getDoor(Direction.EAST).getStatus();
        if(doorStatus == Door.DoorStatus.CLOSED){
            sb.append(" |");
        }
        else if(doorStatus == Door.DoorStatus.OPEN){
            sb.append("  ");
        }
        else{
            sb.append(" L");
        }
        sb.append("\n");

        // Row 3: South Door
        sb.append("* ");
        doorStatus = getDoor(Direction.SOUTH).getStatus();
        if(doorStatus == Door.DoorStatus.CLOSED){
            sb.append("-");
        }
        else if(doorStatus == Door.DoorStatus.OPEN){
            sb.append(" ");
        }
        else{
            sb.append("L");
        }
        sb.append(" *");

        return sb.toString();
    }

    public static void main(String[] args){
        Room testRoom = new Room();

        // print room with all doors closed
        System.out.println(testRoom);
        System.out.println();

        // lock north door and print
        testRoom.getDoor(Direction.NORTH).setStatus(Door.DoorStatus.LOCKED);
        System.out.println(testRoom);
        System.out.println();

        // lock west door and print
        testRoom.getDoor(Direction.WEST).setStatus(Door.DoorStatus.LOCKED);
        System.out.println(testRoom);
        System.out.println();

        // lock east door and print
        testRoom.getDoor(Direction.EAST).setStatus(Door.DoorStatus.LOCKED);
        System.out.println(testRoom);
        System.out.println();

        // lock south door and print
        testRoom.getDoor(Direction.SOUTH).setStatus(Door.DoorStatus.LOCKED);
        System.out.println(testRoom);
        System.out.println();
    }
}
