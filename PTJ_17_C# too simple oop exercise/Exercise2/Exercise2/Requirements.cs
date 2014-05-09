using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise2
{
    class Requirements
    {
        private Components c;   // first
        private int count;  // The number of this c

        /* Constructor  */
        public Requirements(Components c, int count)
        {
            this.c = c;
            this.count = count;
        }

        /* Member function */
        public void modifyRequir(Components c,int count)
        {
            this.c = c;
            this.count = count;
        }
    }
}
