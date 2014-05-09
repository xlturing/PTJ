using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise2
{
    class Demands
    {
        private List<Requirements> lr;  // A collection of zero to four instances of the Requirements class
        public Demands()
        {
            lr = new List<Requirements>();
        }

        /* Addition requirements member function */
        public void addReq(Requirements r)
        {
            lr.Add(r);
        }

        /* Removal  requirements at index i member function */
        public void removeReq(int i)
        {
            lr.RemoveAt(i);
        }

        /* The number of Requirements in this provider */
        public int countReq()
        {
            return lr.Count;
        }
    }
}
