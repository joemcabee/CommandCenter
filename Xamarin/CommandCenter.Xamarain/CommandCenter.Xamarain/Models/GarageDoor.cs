using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommandCenter.Xamarain.Models
{
    public class GarageDoor
    {
        public string Service { get; set; }
        public string Car { get; set; }

        public string DoorSide
        {
            get
            {
                return String.Join(" ", this._DoorSide, "Garage Door");
            }
            set
            {
                this._DoorSide = value;
            }
        }

        private string _DoorSide = String.Empty;
    }
}
