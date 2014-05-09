using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise2
{
    /* An enumerated type, representing the possible resource types in the program */
    public enum Components
    {
        Hardware,
        Firmware,
        Software,
        Other,
    }

    class MainClass
    {
        public static int  multiples(Provider p, Demands d)
        {
            return d.countReq() / p.countRes();
        }

        public static void Main(string[] args)
        { 
            Random r = new Random();    // generate random integer
           
            // Create an instance of a Provider with different numbers and combinations of Resources
            Provider p = new Provider();
            p.addRes(new Resource(Components.Firmware,r.Next(1,100)));
            p.addRes(new Resource(Components.Hardware,r.Next(1,100)));
            p.addRes(new Resource(Components.Software, r.Next(1, 100)));
            p.addRes(new Resource(Components.Other, r.Next(1, 100)));
            
            // Create an instance of a Demands with different numbers and combinations of Requirements
            Demands d = new Demands();
            d.addReq(new Requirements(Components.Firmware, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Hardware, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Software, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Other, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Other, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Other, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Other, r.Next(1, 100)));
            d.addReq(new Requirements(Components.Other, r.Next(1, 100)));
            // Verify that the value returned from the method is correct.
            Console.WriteLine(multiples(p, d));

            /* Hold on to look at result */
            Console.ReadLine();
        }
    }
}
