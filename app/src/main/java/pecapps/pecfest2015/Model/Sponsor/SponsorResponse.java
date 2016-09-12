package pecapps.pecfest2015.Model.Sponsor;

import java.util.Random;

/**
 * Created by jatin on 20/8/16.
 */
public class SponsorResponse {
    public int count;
    public Sponsor sponsorlist[];

    public void randomizeList(){

        Random r=new Random();
        for(int i=0;i<(sponsorlist.length/2);i++){
            int x= r.nextInt(sponsorlist.length);
            int y= r.nextInt(sponsorlist.length);
            if(y==x){
                y=(y+1)%sponsorlist.length;
            }
            Sponsor temp=sponsorlist[x];
            sponsorlist[x]=sponsorlist[y];
            sponsorlist[y]=temp;

        }
    }
}
