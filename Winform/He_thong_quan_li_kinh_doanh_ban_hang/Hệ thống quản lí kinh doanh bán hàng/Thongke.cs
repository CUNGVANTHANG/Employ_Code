using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace He_thong_kinh_doanh_Pizza
{
    public partial class Thống_kê : Form
    {
        public Thống_kê()
        {
            InitializeComponent();
        }

        private void Thống_kê_FormClosed(object sender, FormClosedEventArgs e)
        {
            Form1 f = new Form1();
            f.Show();
            this.Hide();
        }
    }
}
