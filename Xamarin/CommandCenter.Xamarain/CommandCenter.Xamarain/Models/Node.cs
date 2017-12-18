using System;

namespace CommandCenter.Xamarain.Models
{
    public class Node
    {
        public string Name { get; set; }
        public string Type { get; set; }
        public string Status { get; set; }
        public string Service { get; set; }
        public int Position { get; set; }
        public string IpAddress { get; set; }
    }
}
