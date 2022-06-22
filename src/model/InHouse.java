package model;

/** InHouse Part. Inherited class from Part.
 *
 */
public class InHouse extends Part{
    public int machineID;

    /** Creates an In-House Part
     *
     * @param id Part ID
     * @param name Part Name
     * @param price Part price
     * @param stock Part Inventory Level
     * @param min Part min
     * @param max Part max
     * @param machineID Part Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @return the MachineID
     */
    public int getMachineID(){
        return machineID;
    }

    /**
     *
     * @param machineID the MachineID to set
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }
}
