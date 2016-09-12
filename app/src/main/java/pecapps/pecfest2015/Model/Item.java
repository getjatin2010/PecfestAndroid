package pecapps.pecfest2015.Model;

/**
 * Created by Bansal on 8/21/2016.
 */
public class Item {



        private String title;
        private String description;
        private String number;

        public Item(String title, String description,String number) {
            super();
            this.title = title;
            this.description = description;
            this.number=number;
        }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getnumber() {
        return number;
    }
    // getters and setters...
}
