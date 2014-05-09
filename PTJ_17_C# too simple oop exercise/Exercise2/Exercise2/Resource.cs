using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise2
{
    class Resource
    {
        private Components c;   // first
        private int count;  // The number of this c
        public Resource(Components c, int count)
        {
            this.c = c;
            this.count = count;
        }

        /* Member function */
        public void modifyRequir(Components c, int count)
        {
            this.c = c;
            this.count = count;
        }
    }
}
