# Hadoop

* [Input]

  The input includes multiple files. In each file, there is a string at the beginning of each line,representing a user, followed by a colon. After the colon is a list of strings representing the friends of the user, these strings are separated by commas. It is guaranteed that, if A is in B’s friends list, B is also in A’s friends list.

* [Output]

  The output only contains one file, there is a pair of strings at the beginning of each line, written as {A,B}, representing a pair of users, followed by a list of strings representing the mutual friends of the two users A and B.

* [Sample Input]

  File1.txt:

  Alice:Bob,Cindy,David,Eric
  Bob:Alice,David,Eric
  Cindy:Alice,Eric
  File2.txt:
  David:Alice,Bob
  Eric:Alice,Bob,Cindy

* [Sample Output]

  Result.txt:
  {Alice,Bob} David,Eric
  {Alice,Cindy} Eric
  {Alice,David} Bob
  {Alice,Eric} Bob,Cindy
  {Bob,Cindy} Eric,Alice
  {Bob,David} Alice
  {Bob,Eric} Alice
  {Cindy,David} Alice
  {Cindy,Eric} Alice
  {David,Eric} Alice,Bob

# Spark

use Spark RDD operations to get results of following tow SQLs.

1. select type, sum(value) from Device, DValues where id = did and did > 0 and did < 1000 and date is null group by type order by type;


2. select date, type, avg(value) from Device left join DValues on id = did and did > 0 and did < 1000 and date is null group by date, type order by date, type;