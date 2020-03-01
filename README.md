# AlgorithmContestInfo
算法竞赛信息爬虫，提供近期ACM/OI或其他类型算法竞赛信息。<br/>
提供与 http://contests.acmicpc.info/contests.json 一致的接口<br/>
其他信息等待完善

## 官方数据源
http://algcontest.rainng.com/<br/><br/>
本数据源允许直接引用，但请标注以下文字，谢谢。<br/>
**数据来源http://algcontest.rainng.com/**<br/>
已开启跨域，支持Https，可以使用JavaScript等前端语言调用

## API
* /contests 获取所有类型竞赛信息
* /contests/acm 获取ACM竞赛信息
* /contests/oi 获取OI竞赛信息

## 兼容IcpcInfo信息站API
* /contests/old 获取所有类型竞赛信息
* /contests/old/acm 获取ACM竞赛信息
* /contests/old/oi 获取OI竞赛信息

官方数据源完全兼容老ACM竞赛信息站，您可以将contests.acmicpc.info重定向至algcontest.rainng.com

## FAQ
Q: 为什么没有一些知名OJ的爬虫实现<br/>
A: 这些OJ在近期未举办比赛或没有举办比赛的意向，当其举办比赛时会开发<br/>
<br/>
Q: 目前支持哪些站点？
* [CodeForces](https://codeforces.com/contests)
* [CometOJ](https://cometoj.com/contests)
* [计蒜客](https://nanti.jisuanke.com/contest)
* [LeetCode](https://leetcode.com/contest/)
* [洛谷](https://www.luogu.org/contest/list)
* [牛客网](https://ac.nowcoder.com/acm/home)
