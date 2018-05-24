using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Threading;
using System.ComponentModel;
using System.IO;

namespace EL_Competition
{
    /// <summary>
    /// Model2.xaml 的交互逻辑
    /// </summary>
    public partial class Model2 : Window
    {
        public bool issigned = false;
        public string username;

        String SaveLoc = Model1Setup.SaveLoc;
        BackgroundWorker timer = new BackgroundWorker();
        DateTime startTime;
        System.Timers.Timer timerf = new System.Timers.Timer(1000);

        public Model2()
        {
            InitializeComponent();

            timer.WorkerReportsProgress = true;
            timer.WorkerSupportsCancellation = true;
            timer.DoWork += Timer_DoWork;
            timer.ProgressChanged += Timer_ProgressChanged;
            Loading();
            timer.RunWorkerAsync();
        }
        bool isinSmode = false,isfreetime = false;
        DateTime finaltime = new DateTime();
        TimeSpan continueoustime = new TimeSpan(), durationtime = new TimeSpan();

        private void SavingData()
        {
            if (issigned)
            {
                List<string> things = new List<string>();
                using (StreamReader re = new StreamReader(SaveLoc + @"/" + username + ".ini"))
                {
                    while (re.Peek() >= 0)
                    {
                        things.Add(re.ReadLine());
                    }
                }
                using (StreamWriter wr = new StreamWriter(SaveLoc + @"/" + username + ".ini"))
                {
                    
                    foreach (string i in things)
                    {
                        wr.WriteLine(i);
                    }
                    string extra;
                    if (timeup)
                    {
                        extra = " 坚持完成";
                    }
                    else
                    {
                        extra = " 半途而废";
                    }
                    wr.WriteLine(DateTime.Now.ToString() + extra + " 专注模式：专注桌面 专注时间：" + Convert.ToInt32(durationtime.TotalMinutes).ToString() + "分钟");
                }
            }
        }

        private void Loading()
        {
            using (StreamReader setting = new StreamReader(SaveLoc + @"\setting2.ini"))
            {
                int ss = 0;
                while (!File.Exists(SaveLoc + @"\setting2.ini"))
                {
                    Thread.Sleep(5);
                    ss++;
                    if (ss > 5)
                    {
                        MessageBox.Show("设置加载出错");
                        Close();
                    }
                }
                List<string> settings = new List<string>();
                while (setting.Peek() >= 0)
                {
                    settings.Add(setting.ReadLine());
                }
                if (settings[0] == "Strong")
                {
                    isinSmode = true;
                    exit.IsEnabled = false;
                    exit.Visibility = Visibility.Hidden;
                }
                
                if (settings[1] == "1" || settings[1] == "2")
                {
                    finaltime = Convert.ToDateTime(settings[2]);
                    continueoustime = finaltime - DateTime.Now;
                }
                else
                {
                    isfreetime = true;
                    continueoustime = TimeSpan.FromSeconds(30);
                    progress.Visibility = Visibility.Hidden;
                    progresstext.Visibility = Visibility.Hidden;
                }
                if (continueoustime < new TimeSpan(0))
                {
                    setting.Close();
                    File.Delete(SaveLoc + @"\setting2.ini");
                    MessageBox.Show("检测到上次专注以非正常方式结束，已清除缓存，请重启");
                    Close();
                }
                if (settings[3] == "1")
                {
                    ImageBrush b = new ImageBrush();
                    b.ImageSource = new BitmapImage(new Uri(settings[4], UriKind.Absolute));
                    this.Background = b;
                }
            }//读取预设
        }
        bool isshowing = false;
        Thread a;
        private void Window_MouseMove(object sender, MouseEventArgs e)
        {
            if (!isshowing&&!timeup)
            {
                isshowing = true;
                string[] sayings = new string[10] {
                    "只要不放弃，就没有什么能让自己退缩；只要够坚强，就没有什么能把自己打垮！",
                    "慢慢地，你不会再流泪；慢慢地，一切都过去了…适当的放弃，是人生优雅的转身。",
                    "逆境总是有的，人生总要进击。愿你不要屈从于命运的安排，坚韧不拔，锲而不舍！做永远的生活强者！",
                    "人生路上没有永远的成功，只有永远的奋斗。",
                    "坚持是毅力，仿佛一轮炽热不落的艳阳；坚持是灵魂，仿佛一群屹立不倒的山林。",
                    "在成功的道路上，坚持是非常重要的，面对挫折时，要告诉自己：饱尝甜头。",
                    "生命不在长而在于好，只要每一次尽力的演示，都值得鼓励与喝采。",
                    "有理想在的地方，地狱就是天堂。有希望在的地方，痛苦也成欢乐。",
                    "觉得自己做得到和做不到，只在一念之间。",
                    "功到自然成，成功之前难免有失败，然而只要能克服困难，坚持不懈地努力，那么，成功就在眼前。" };
                a = new Thread(() =>
                {
                    Dispatcher.Invoke(new Action(() =>
                    {
                        string words = "";
                        Random ran = new Random();
                        ran.Next(0, 10);
                        if (durationtime>TimeSpan.FromSeconds(12)&& durationtime < TimeSpan.FromMinutes(2))
                        {
                           words = "才开始专注呢！！专注时要做到心无旁骛，不要再碰鼠标键盘了！！\r\n" + sayings[ran.Next(0, 10)] + "加油！！";
                        }
                        if(durationtime > TimeSpan.FromMinutes(2))
                        {
                            words= "恭喜你已经专注了" + (durationtime.Minutes+durationtime.Hours*60).ToString() + "分钟了，继续努力！\r\n" + sayings[ran.Next(0, 10)] + "加油！！";
                        }
                        if (!isfreetime&&durationtime > TimeSpan.FromSeconds(12))
                        {
                            words += "\r\n克制自己让下面的进度条走完哦";
                        }
                        conversation.Text = words;
                        conversation.Visibility = Visibility.Visible;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Red;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Orange;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Yellow;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Green;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Aqua;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Blue;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.Purple;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Foreground = Brushes.White;
                    }));
                    Thread.Sleep(500);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversation.Visibility = Visibility.Hidden;
                        isshowing = false;
                    }));
                });
                a.Start();
            }
        }

        private void Timer_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            secondPointer.Angle = DateTime.Now.Second * 6;
            minutePointer.Angle = DateTime.Now.Minute * 6;
            hourPointer.Angle = (DateTime.Now.Hour * 30) + (DateTime.Now.Minute * 0.5);
            this.labTime.Content = DateTime.Now.ToString("HH:mm:ss");
            labtt.Content = DateTime.Now.Year.ToString();
            if (!isfreetime)
            {
                progress.Value = durationtime.TotalMilliseconds/(finaltime - startTime).TotalMilliseconds *100;
                continueoustime = finaltime - DateTime.Now;
                progresstext.Text = "据专注结束还有" + continueoustime.Hours + "小时" + continueoustime.Minutes + "分钟" + continueoustime.Seconds + "秒"; ;
            }
            if (timeup)
            {
                conversation.Text = "专注结束，恭喜你";
                conversation.Visibility = Visibility.Visible;
                exit.Visibility = Visibility.Visible;
                exit.Content = "结束专注";
                progresstext.Text = "时间到";
                end = new Thread(() =>
                {
                    while (true)
                    {
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Red;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Orange;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Yellow;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Green;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Aqua;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Blue;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.Purple;
                        }));
                        Thread.Sleep(500);
                        Dispatcher.Invoke(new Action(() =>
                        {
                            exit.Foreground = Brushes.White;
                        }));
                    }
                });
                end.Start();
            }
        }

        Thread end;
        bool timeup = false;
        private void Timer_DoWork(object sender, DoWorkEventArgs e)
        {
            startTime = DateTime.Now;
            //continueoustime = DateTime.Now- finaltime;
            while (!timer.CancellationPending && continueoustime > TimeSpan.FromSeconds(0))
            {
                durationtime = DateTime.Now - startTime;
                timer.ReportProgress(0);
                Thread.Sleep(30);
            }
            if (!timer.CancellationPending)
            {
                timeup = true;
                timer.ReportProgress(0);
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if (MessageBox.Show("真的要放弃么？", "请坚持哦", MessageBoxButton.YesNo) == MessageBoxResult.Yes&&!timeup)
            {
                SavingData();
                File.Delete(SaveLoc + @"\setting2.ini");
                timer.CancelAsync();
                if(a.IsAlive)
                a.Abort();
                Close();
            }
            if (timeup)
            {
                SavingData();
                File.Delete(SaveLoc + @"\setting2.ini");
                if (end.IsAlive)
                {
                    end.Abort();
                }
                Close();
            }
        }

        
    }
}
