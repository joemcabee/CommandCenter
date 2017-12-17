using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommandCenter.Xamarain.Models
{
    public class Sensor
    {
        public string Type { get; set; }
        public string Location { get; set; }
        public string Value { get; set; }
        public string Service { get; set; }
        public int Position { get; set; }
    }
}
