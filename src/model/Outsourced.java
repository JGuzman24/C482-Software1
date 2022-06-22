package model;

/** Outsourced Part. Inherited class from Part.
 *
 */
public class Outsourced extends Part{
    public String companyName;

    /** Creates an In-House Part
     *
     * @param id Part ID
     * @param name Part Name
     * @param price Part price
     * @param stock Part Inventory Level
     * @param min Part min
     * @param max Part max
     * @param companyName Part Company Name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return the Company Name
     */
    public String getCompanyName(){
        return companyName;
    }

    /**
     *
     * @param companyName the Company Name to Set
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
}
