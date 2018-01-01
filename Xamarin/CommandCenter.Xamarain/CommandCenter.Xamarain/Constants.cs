namespace CommandCenter.Xamarain
{
    public class Authorization
    {
        public string Username = "rogueone";
        public string Password = "deathstar";
    }

    public class Urls
    {
        public static string CommandCenter = "https://command-center.ddns.net/";

        public static string GarageWebcam = "https://command-center.ddns.net/pigarage/webcam/stream.jpg";
        public static string RobotWebcam = "https://command-center.ddns.net/pirobot/webcam/stream.jpg";
    }

    public class RemoteServices
    {
        public static string CommandCenterPing = "ping";
        public static string GaragePing = "proxy/GaragePing";
        public static string RobotPing = "proxy/RobotPing";
        public static string SensorPing = "proxy/SensorPing";
        public static string EnviroPing = "proxy/EnviroPing";
        public static string LeftGarageDoor = "proxy/LeftGarageDoor";
        public static string RightGarageDoor = "proxy/RightGarageDoor";
        public static string Temp = "proxy/Temp";
        public static string Light = "proxy/Light";
        public static string EnviroTemp = "proxy/EnviroTemp";
        public static string EnviroLight = "proxy/EnviroLight";
    }
}
