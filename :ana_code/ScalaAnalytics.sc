val nba2008 = sc.textFile("hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/nba2008.csv")
val nba2016 = sc.textFile("hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/nba2016.csv")

val okc2008 = nba2008.filter(line => line.contains("OKC"))
val okc2016 = nba2008.filter(line => line.contains("OKC"))
val lal2008 = nba2008.filter(line => line.contains("LAL"))
val lal2016 = nba2016.filter(line => line.contains("LAL"))

val worst2008: String = "OKC"
var okc2008makes: Int = 0
var okc2008attempts: Int = 0
var okc2008wins: Int = 0

var okc2016makes: Int = 0
var okc2016attempts: Int = 0
var okc2016wins: Int = 0

val best2008: String = "LAL"
var lal2008makes: Int = 0
var lal2008attempts: Int = 0
var lal2008wins: Int = 0

var lal2016makes: Int = 0
var lal2016attempts: Int = 0
var lal2016wins: Int = 0

okc2008makes = okc2008.map{x => x.split(',')}.map{x => (x(2).toInt)}.sum().toInt
okc2008attempts = okc2008.map{x => x.split(',')}.map{x => (x(4).toInt)}.sum().toInt
okc2008wins = okc2008.map{x => x.split(',')}.map{x => (x(5).toInt)}.sum().toInt
val okc2008P: Double = (okc2008makes).toDouble/(okc2008attempts).toDouble


okc2016makes = okc2016.map{x => x.split(',')}.map{x => (x(2).toInt)}.sum().toInt
okc2016attempts = okc2016.map{x => x.split(',')}.map{x => (x(4).toInt)}.sum().toInt
okc2016wins = okc2016.map{x => x.split(',')}.map{x => (x(5).toInt)}.sum().toInt
val okc2016P: Double = (okc2016makes).toDouble/(okc2016attempts).toDouble


lal2008makes = lal2008.map{x => x.split(',')}.map{x => (x(2).toInt)}.sum().toInt
lal2008attempts = lal2008.map{x => x.split(',')}.map{x => (x(4).toInt)}.sum().toInt
lal2008wins = lal2008.map{x => x.split(',')}.map{x => (x(5).toInt)}.sum().toInt
val lal2008P: Double = (lal2008makes).toDouble/(lal2008attempts).toDouble

lal2016makes = lal2016.map{x => x.split(',')}.map{x => (x(2).toInt)}.sum().toInt
lal2016attempts = lal2016.map{x => x.split(',')}.map{x => (x(4).toInt)}.sum().toInt
lal2016wins = lal2016.map{x => x.split(',')}.map{x => (x(5).toInt)}.sum().toInt
val lal2016P: Double = (lal2016makes).toDouble/(lal2016attempts).toDouble

println("2008 Worst Team: OKC \n3PM: " + okc2008makes + "\n3PA: " + okc2008attempts + "\n3PP: " + okc2008P + "\nWins: " + okc2008wins)
println("2016 OKC: \n3PM: " + okc2016makes + "\n3PA: " + okc2016attempts + "\n3PP: " + okc2016P + "\nWins: " + okc2016wins)

println("2008 Best Team: LAL \n3PM: " + lal2008makes + "\n3PA: " + lal2008attempts + "\n3PP: " + lal2008P + "\nWins: " + lal2008wins)
println("2016 LAL: \n3PM: " + lal2016makes + "\n3PA: " + lal2016attempts + "\n3PP: " + lal2016P + "\nWins: " + lal2016wins)
