# gnuplot
# Chaos.gnu by Mathew Peet
  unset multiplot
#
#set term postscript enhanced eps "Helvetica" 12
#set nokey

set yrange[0:1]
set pointsize 0.05

set output "chaos-plot.eps"
set multiplot
set origin 0,0
set size 1,1
set xlabel "r"
set ylabel "x"
set xrange [0:4]
plot 'out0.5' using ($3<300 ? 1/0: $1):2 title "Populations"
