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
using System.IO;

namespace EL_Competition
{
    /// <summary>
    /// SignInWindow.xaml 的交互逻辑
    /// </summary>
    public partial class SignInWindow : Window
    {
        string SaveLoc = Model1Setup.SaveLoc;
        public SignInWindow()
        {
            InitializeComponent();
            password.TextDecorations = new TextDecorationCollection(new TextDecoration[] {
                new TextDecoration() {
                     Location= TextDecorationLocation.Strikethrough,
                      Pen= new Pen(Brushes.Black, 10f) {
                          DashCap =  PenLineCap.Round,
                           StartLineCap= PenLineCap.Round,
                            EndLineCap= PenLineCap.Round,
                             DashStyle= new DashStyle(new double[] {0,1.2 }, 2f)
                      }
                }

            });
            password_Copy.TextDecorations = new TextDecorationCollection(new TextDecoration[] {
                new TextDecoration() {
                     Location= TextDecorationLocation.Strikethrough,
                      Pen= new Pen(Brushes.Black, 10f) {
                          DashCap =  PenLineCap.Round,
                           StartLineCap= PenLineCap.Round,
                            EndLineCap= PenLineCap.Round,
                             DashStyle= new DashStyle(new double[] {0.0,1.2 }, 2f)
                      }
                }

            });
        }
        bool issignnewing = false;

        public string getUsername()
        {
            return username.Text;
        }
        bool issigned = false;

        public bool hasSigned()
        {
            return issigned;
        }

        private void sign_Click(object sender, RoutedEventArgs e)
        {
            if (issignnewing)
            {
                if (username.Text.Length > 8)
                {
                    MessageBox.Show("超过用户名最大长度");
                    return;
                }
                if (File.Exists(SaveLoc + @"\" + username.Text + ".ini"))
                {
                    MessageBox.Show("用户已存在");
                    return;
                }
                if (username.Text == "" || password.Text == "")
                {
                    MessageBox.Show("用户名或密码不能为空");
                    return;
                }
                if (password.Text != password_Copy.Text)
                {
                    MessageBox.Show("两次密码输入不一致");
                    return;
                }
                if (!Directory.Exists(SaveLoc))
                {
                    Directory.CreateDirectory(SaveLoc);
                }
                using (StreamWriter newuser = new StreamWriter(SaveLoc +@"/"+ username.Text+".ini"))
                {
                    
                    newuser.WriteLine(password.Text);
                }
                sign.Content = "登陆";
                repeatname.Visibility = Visibility.Hidden;
                password_Copy.Visibility = Visibility.Hidden;
                password_Copy.Clear();
                password.Clear();
                MessageBox.Show("注册成功");
                issignnewing = false;
            }
            else
            {
                if(!File.Exists(SaveLoc + @"\" + username.Text +".ini"))
                {
                    MessageBox.Show("用户名不存在");
                    return;
                }
                using (StreamReader user = new StreamReader(SaveLoc + @"\" + username.Text + ".ini"))
                {
                    string passwords = user.ReadLine();
                    if (password.Text != passwords)
                    {
                        MessageBox.Show("密码不正确");
                    }
                    if(password.Text == passwords)
                    {
                        issigned = true;
                        this.Close();
                    }
                }
            }
        }

        private void signnew_Click(object sender, RoutedEventArgs e)
        {
            sign.Content = "注册";
            repeatname.Visibility = Visibility.Visible;
            password_Copy.Visibility = Visibility.Visible;
            signnew.Visibility = Visibility.Hidden;
            issignnewing = true;
        }

        private void password_Copy_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                sign.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
            }
        }

        private void password_KeyUp(object sender, KeyEventArgs e)
        {
            
            if (e.Key == Key.Enter)
            {
                if (issignnewing)
                {
                    TraversalRequest request = new TraversalRequest(FocusNavigationDirection.Next);
                    UIElement focusElement = Keyboard.FocusedElement as UIElement;
                    if (focusElement != null)
                    {
                        focusElement.MoveFocus(request);
                    }
                }
                else
                {
                    sign.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
                }
            }
        }

        private void username_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                TraversalRequest request = new TraversalRequest(FocusNavigationDirection.Next);
                UIElement focusElement = Keyboard.FocusedElement as UIElement;
                if (focusElement != null)
                {
                    focusElement.MoveFocus(request);
                }
            }
        }
    }
}
