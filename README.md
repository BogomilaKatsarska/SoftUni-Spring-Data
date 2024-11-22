<h1> Spring Data Notes</h1>
<ol>
  <li>Java DB Apps Intro
    <ul>
      <li>Using an external library (MySQL Connector/J) we make a connection via a Driver Manager and a Connection class</li>
      <li> Connection connection = DriverManager.getConnection(url, username, pass);</li>
      <li>We retrieve the result with the ResultSet and the PreparedStatement classes</li>
      <li> PreparedStatement preparedStatement = connection.prepareStatement(query);</li>
      <li> ResultSet resultSet = preparedStatement.executeQuery();</li>
      <li> resultSet.next();</li>
      <li> int employeeCount = resultSet.getInt(1);</li>
      <li>Iterating over result:</li>
      <li> while (resultSet.next()){...}</li>
      <li>JDBC is a standard JAVA API for db-independent connectivity</li>
      <li>JDBC API provides the connection between the application and the DriverManager</li>
      <li>JDBC DriverManager establishes the conn with the correct driver. Supports multiple drivers connected to diff types of dbs</li>
      <li>JDBC Driver handles the communications with the db</li>
      <li>Connection - all methods for contacting a db</li>
      <li>Statement - methods and properties that enable you to send SQL</li>
      <li>PreparedStatement - for SQL statements used many times. Accepts params </li>
      <li> PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");</li>
      <li> stmt.setDouble(1, Double.parseDouble(salary)); //1=param index </li>
      <li>CallableStatement - used for stored procedures. Accepts params.</li>
      <li>ResultSet - retrieved data(set of table rows)</li>
    </ul>
  </li>
  <li> ORM Fundamentals
    <ul>
      <li>@Retention - annotation - how long annotations with the annotated type are to be retained - SOURCE, CLASS, RUNTIME</li>
      <li>@Target - annotation - indicates the contexts in which the annotation interface is appicable - i.e.: METHOD, FIELD, TYPE==class, interface, enum or record, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE..</li>
    </ul>
  </li>
  <li> Intro to Hibernate</li>
  <li> Hibernate Code First</li>
  <li> Spring Data Intro</li>
  <li> Spring Data Advanced Querying</li>
  <li> Spring Data Auto Mapping Objects</li>
  <li> JSON Processing</li>
  <li> XML Processing</li>
  <li> Q&A</li>
  <li> Exam Prep</li>
  <li> Workshop</li>
</ol>
