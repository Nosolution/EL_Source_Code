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
    /// Model1.xaml 的交互逻辑
    /// </summary>
    public partial class Model1 : Window
    {
        public bool issigned = false;
        public string username;

        BackgroundWorker animation = new BackgroundWorker();
        string source,locright,locleft;
        int maxIma;
        bool isAnimationCotinue;//精灵动画支线程
        //playPetsAnimation(最大帧数，动画名称，是否重复播放)播放pets动作动画

        BackgroundWorker timer = new BackgroundWorker();//计时器

        Point pos = new Point();//精灵控件拖拽
        DateTime finaltime = new DateTime();
        TimeSpan continueoustime = new TimeSpan();
        DateTime starttime = DateTime.Now;
        TimeSpan durationtime;

        Thread TimeUpAnimation;

        bool isinSmode = false;

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
                    wr.WriteLine(DateTime.Now.ToString() + extra + " 专注模式：专注小精灵 专注时间：" + Convert.ToInt32(durationtime.TotalMinutes).ToString() + "分钟");
                }
            }
        }

        public Model1()
        {
            InitializeComponent();
            animation.WorkerReportsProgress = true;
            animation.WorkerSupportsCancellation = true;
            animation.ProgressChanged += Animation_ProgressChanged;
            animation.DoWork += Animation_DoWork;//精灵动画支线程声明
            

            timer.WorkerReportsProgress = true;
            timer.WorkerSupportsCancellation = true;
            timer.ProgressChanged += Timer_ProgressChanged;
            timer.DoWork += Timer_DoWork;//timer

            cloud.Source = new BitmapImage(new Uri(@"\Resources\cloud.png", UriKind.Relative));
            Loading();
            timer.RunWorkerAsync();

            TimeUpAnimation = new Thread(()=>
            {
                Dispatcher.Invoke(new Action(() =>
                {
                    if (MessageBox.Show("是否还要继续专注呢？", "专注时间到！", MessageBoxButton.YesNo) == MessageBoxResult.No)
                    {
                        SavingData();
                        Unlock();
                        playPetsAnimation(14, "LifthhAni", true);
                        conversations.Text = "太棒了，恭喜你，"+username+"完成了专注";
                        cloud.Visibility = Visibility.Visible;
                        conversations.Visibility = Visibility.Visible;
                        finishtype = 1;
                    }
                    else
                    {
                        Model1Setup a = new Model1Setup();
                        a.issigned = issigned;
                        a.username = username;
                        a.Show();
                        this.Close();
                    }
                }));
                Thread.Sleep(4000);
                Dispatcher.Invoke(new Action(() =>
                {
                    conversations.Text = "送你一朵小花花作为奖励";
                    playPetsAnimation(15, "ExtendFlour", false);
                }));
                Thread.Sleep(4000);
                Dispatcher.Invoke(new Action(() =>
                {
                    conversations.Text = "那我闪了\r\nbyebye";
                    
                }));
                Thread.Sleep(4000);
                Dispatcher.Invoke(new Action(() =>
                {
                    Close();
                }));
            });

            StartAnimation = new Thread(() =>
            {
                Dispatcher.Invoke(new Action(() =>
                {
                    int ddd = continueoustime.Days * 24;
                    playPetsAnimation(6, "WelcomeAni", true);
                    conversations.Text = "很高心见到你 "+username+"，我是你的专注小精灵。距离本次专注结束还有" + string.Format("{0}小时{1}分钟{2}秒", continueoustime.Hours + ddd, continueoustime.Minutes, continueoustime.Seconds) + "哦";
                    
                }));
                Thread.Sleep(5000);
                Dispatcher.Invoke(new Action(() =>
                {
                    string extra = "";
                    if (isinSmode)
                    {
                        extra = "这可是强力模式，在时间结束之前无法停止或恢复哦";
                    }
                    conversations.Text = "指定的游戏软件都被我给禁止啦，专注期间可不要想着玩他们哦。" + extra;
                }));
                Thread.Sleep(5000);
                Dispatcher.Invoke(new Action(() =>
                {
                    playPetsAnimation(42, "FlourAni", false);
                    conversations.Text = "加油！"+username;
                }));
                Thread.Sleep(5000);
                Dispatcher.Invoke(new Action(() =>
                {
                    conversations.Text = "我会在这里乖乖陪着你的 "+username;
                    playPetsAnimation(9, "Nictation", false);
                }));
                Thread.Sleep(3000);
                Dispatcher.Invoke(new Action(() =>
                {
                    cloud.Visibility = Visibility.Hidden;
                    conversations.Visibility = Visibility.Hidden;
                    playPetsAnimation(9, "Nictation", false);
                    isStardardMode = true;
                }));
            });
            StartAnimation.Start();//初始动画
        }
        Thread StartAnimation;
        bool isStardardMode = false;

        private void Loading()
        {
            using (StreamReader setting = new StreamReader(SaveLoc + @"\setting.ini"))
            {
                int ss = 0;
                while (!File.Exists(SaveLoc + @"\setting.ini"))
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
                }
                finaltime = Convert.ToDateTime(settings[2]);
                continueoustime = finaltime - DateTime.Now;
                if(continueoustime < new TimeSpan(0))
                {
                    setting.Close();
                    File.Delete(SaveLoc + @"\setting.ini");
                    MessageBox.Show("检测到上次专注以非正常方式结束，已清除缓存，请重启");
                    Close();
                }
            }//读取预设
        }


        int finishtype = -1;
        static TimeSpan zerotime = new TimeSpan(0);
        bool shouldNictation = false, shouldsay = false, timeup = false;
        DateTime nictationTime = DateTime.Now;
        private void Timer_DoWork(object sender, DoWorkEventArgs e)
        {
            double remindtime = 1;
            while (continueoustime > zerotime && !timer.CancellationPending)
            {
                if((DateTime.Now - nictationTime) > TimeSpan.FromSeconds(5)&&isStardardMode)
                {
                    nictationTime = DateTime.Now;
                    shouldNictation = true;
                }
                if (durationtime > TimeSpan.FromMinutes(remindtime) && isStardardMode)
                {
                    remindtime = remindtime + 0.2;
                    shouldsay = true;
                    shouldNictation = false;
                    isStardardMode = false;
                }                
                continueoustime = finaltime - DateTime.Now;
                durationtime = DateTime.Now - starttime;
                timer.ReportProgress(0);
                Thread.Sleep(30);
            }
            timeup = true;
            if (!timer.CancellationPending)
            {
                isStardardMode = false;
                finishtype = 0;
                timer.ReportProgress(0);
            }
        }//timer
        

        private void Timer_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            if (shouldNictation)
            {
                playPetsAnimation(9, "Nictation", false);
                shouldNictation = false;
            }
            if(finishtype == -1)
            {
                timeprogressbar.Value = 100*(durationtime.TotalSeconds/(finaltime-starttime).TotalSeconds);
                int ddd = continueoustime.Days * 24;
                timeshow.Text = "距离专注结束还有：\r\n" + string.Format("{0}小时{1}分钟{2}秒", continueoustime.Hours + ddd, continueoustime.Minutes, continueoustime.Seconds) ;
            }
            if(finishtype == 0)
            {
                TimeUpAnimation.Start();
            }
            if (shouldsay)
            {
                shouldsay = false;
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
                Thread a = new Thread(() =>
                {
                    Dispatcher.Invoke(new Action(() =>
                    {
                        Random ran = new Random();
                        ran.Next(0, 10);
                        conversations.Text = "恭喜你已经专注了"+durationtime.Minutes.ToString()+"分钟了，"+sayings[ran.Next(0, 10)]+"加油！！";
                        cloud.Visibility = Visibility.Visible;
                        conversations.Visibility = Visibility.Visible;
                        playPetsAnimation(14, "Bowlshake", true);
                    }));
                    Thread.Sleep(5000);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        cloud.Visibility = Visibility.Hidden;
                        conversations.Visibility = Visibility.Hidden;
                        isStardardMode = true;
                    }));
                });
                a.Start();
                
            }
        }//Timer

        
        


                int i;
        private void Animation_DoWork(object sender, DoWorkEventArgs e)
        {
            bool f = true;
            while ((isAnimationCotinue||f)&&!animation.CancellationPending)
            {
                for (i = 1; i <= maxIma; i++)
                {
                    source = locleft + i.ToString() + locright;
                    Thread.Sleep(100);
                    animation.ReportProgress(0);
                }
                f = false;
            }
        }

        private void Animation_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            petsi.Source = new BitmapImage(new Uri(source, UriKind.Relative));
        }

        private void Window_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                double dx = -(e.GetPosition(null).X - pos.X) + pets.Margin.Right;
                double dy = -(e.GetPosition(null).Y - pos.Y) + pets.Margin.Bottom;
                pets.Margin = new Thickness(0, 0, dx, dy);
                pos = e.GetPosition(null);

            }
            //拖拽
        }

        bool isfirstclick = true;
        ContextMenu rcmenu;
        MenuItem exit = new MenuItem { Header = "提前结束专注" };
        MenuItem setting = new MenuItem { Header = "设置" };//右键菜单
        MenuItem hidepets = new MenuItem() { Header = "隐藏到屏幕边缘" };
        private void pets_MouseRightButtonUp(object sender, MouseButtonEventArgs e)
        {
            if (isfirstclick)
            {
                rcmenu = new ContextMenu();
                rcmenu.Items.Add(setting);
                rcmenu.Items.Add(exit);
                rcmenu.Items.Add(hidepets);
                isfirstclick = false;
                exit.Click += Exit_Click;
                setting.Click += Setting_Click;
                hidepets.Click += Hidepets_Click;
            }
                pets.ContextMenu = rcmenu;
            
        }
        bool isfirstHide = true;
        private void Hidepets_Click(object sender, RoutedEventArgs e)
        {
            if (isfirstHide)
            {
                broadpets.Source = new BitmapImage(new Uri("/Resources/broader.png", UriKind.Relative));
                broadpets.MouseEnter += Broadpets_MouseEnter;
                broadpets.MouseLeave += Broadpets_MouseLeave;
                broadpets.MouseLeftButtonUp += Broadpets_MouseLeftButtonUp;
                isfirstHide = false;
                conversations.Text = "在这里找到我 "+username;
                conversations.Visibility = Visibility.Visible;
                cloud.Visibility = Visibility.Visible;
                Thread t = new Thread(() =>
                {
                    Thread.Sleep(5000);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversations.Visibility = Visibility.Hidden;
                        cloud.Visibility = Visibility.Hidden;
                    }));
                });
                t.Start();
            }
            pets.Children.Remove(cloud);
            pets.Children.Remove(conversations);
            broadconversations.Children.Add(cloud);
            broadconversations.Children.Add(conversations);
            broadconversations.Visibility = Visibility.Visible;
            pets.Visibility = Visibility.Hidden;
        }

        private void Broadpets_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
        {
            broadconversations.Children.Remove(cloud);
            broadconversations.Children.Remove(conversations);
            pets.Children.Add(cloud);
            pets.Children.Add(conversations);
            broadconversations.Visibility = Visibility.Hidden;
            pets.Visibility = Visibility.Visible;
            
        }

        private void Broadpets_MouseLeave(object sender, MouseEventArgs e)
        {
            broadconversations.Margin = new Thickness(0, 0, -60, 200);
        }

        private void Broadpets_MouseEnter(object sender, MouseEventArgs e)
        {
            broadconversations.Margin = new Thickness(0, 0, -30, 200);
        }


        private void Setting_Click(object sender, RoutedEventArgs e)
        {
            
            Model1Setup tem = new Model1Setup();
            tem.isfirstclick = false;
            tem.fileloc.Text = "";
            tem.Show();
            tem.Closing += Tem_Closing;
        }

        private void Tem_Closing(object sender, CancelEventArgs e)
        {
            Loading();
        }

        

        string SaveLoc = Model1Setup.SaveLoc;
        private void Exit_Click(object sender, RoutedEventArgs e)
        {
            if (MessageBox.Show("确定要半途而废吗？", "专注小精灵", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
            {
                SavingData();
                isStardardMode = false;
                timer.CancelAsync();
                Unlock();
                playPetsAnimation(5,"Angry",true);
                conversations.Text = "半途而废不是好孩子";
                cloud.Visibility = Visibility.Visible;
                conversations.Visibility = Visibility.Visible;
                if(StartAnimation.IsAlive)
                {
                    StartAnimation.Abort();
                }
                Thread t = new Thread(() =>
                {
                    Thread.Sleep(5000);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        conversations.Text = "哼!!!\r\n我闪了";
                    }));
                    Thread.Sleep(5000);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        playPetsAnimation(14, "PuuuAni", true);
                    }));
                    Thread.Sleep(3000);
                    Dispatcher.Invoke(new Action(() =>
                    {
                        Close();
                    }));
                });
                t.Start();
            }
        }

        private void Unlock()
        {
            if (File.Exists(SaveLoc + @"\filebaning.ini"))
            {
                using (StreamReader filebaning = new StreamReader(SaveLoc + @"\filebaning.ini"))
                {
                    while (filebaning.Peek() >= 0)
                    {
                        string i = filebaning.ReadLine();
                        String[] a = i.Split('\\');
                        String filename = a[a.Length - 1];
                        if (File.Exists(i)) File.Delete(i);
                        File.Copy(SaveLoc + @"\" + filename + ".ini", i);
                        File.Delete(SaveLoc + @"\" + filename + ".ini");
                        MessageBox.Show(filename + "解除禁止");
                    }
                }
            }
            File.Delete(SaveLoc + @"\filebaning.ini");
            File.Delete(SaveLoc + @"\setting.ini");
        }

        private void pets_MouseEnter(object sender, MouseEventArgs e)
        {
            progress.Visibility = Visibility.Visible;
        }

        private void pets_MouseLeave(object sender, MouseEventArgs e)
        {
            progress.Visibility = Visibility.Hidden;
        }

        private void Window_MouseUp(object sender, MouseButtonEventArgs e)
        {
            pets.ReleaseMouseCapture();//拖拽

        }

        private void pets_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            pos = e.GetPosition(null);
            pets.CaptureMouse();
            pets.Cursor = Cursors.Hand;
        }//拖拽

        private void playPetsAnimation(int max,string animationName,bool isanicons)
        {
            locleft = "/Resources/" + animationName + "/" + animationName + " (";
            locright = ").png";
            maxIma = max;
            isAnimationCotinue = isanicons;
            if (animation.IsBusy == false) animation.RunWorkerAsync();
            else i = 1;
        }
    }
}