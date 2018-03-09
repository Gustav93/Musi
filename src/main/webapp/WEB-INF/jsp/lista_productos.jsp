<%@ page import="Feeds.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: gsanchez
  Date: 1/2/2018
  Time: 09:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Buscar en Productos</title>

    <!-- Bootstrap -->
    <link href="assets/gentelella-master/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="assets/gentelella-master/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="assets/gentelella-master/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="assets/gentelella-master/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="assets/gentelella-master/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="assets/gentelella-master/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="assets/gentelella-master/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="assets/gentelella-master/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="assets/gentelella-master/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="assets/gentelella-master/build/css/custom.css" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <!--top navigation-->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle" >
                        <div>
                            <a id="menu_toggle" href="ConsultarHistorico.html"><i class="fa fa-arrow-left"></i></a>
                        </div>
                    </div>
                    <div style="float: left;">
                        <h1>Historico</h1>
                    </div>
                </nav>
            </div>
        </div>
        <!--/top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <!--<h3>Lista de Precios</h3>-->
                    </div>

                    <!--<div class="title_right">-->
                    <!--<div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">-->
                    <!--<div class="input-group">-->
                    <!--<input type="text" class="form-control" placeholder="Search for...">-->
                    <!--<span class="input-group-btn">-->
                    <!--<button class="btn btn-default" type="button">Go!</button>-->
                    <!--</span>-->
                    <!--</div>-->
                    <!--</div>-->
                    <!--</div>-->
                </div>

                <div class="clearfix"></div>

                <div class="row">

                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Productos</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#">Settings 1</a>
                                            </li>
                                            <li><a href="#">Settings 2</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">

                                <table id="grilla" class="table">
                                    <thead>
                                        <tr>
                                            <th>Code</th>
                                            <th>ean</th>
                                            <th>Brand</th>
                                            <th>Name</th>
                                            <th>Category</th>
                                            <th>Weight</th>
                                            <th>Online Date Time</th>
                                            <th>Offline Date Time</th>
                                            <th>Approval Status</th>
                                            <th>Description</th>
                                            <th>Import Origin</th>
                                            <th>Processed</th>
                                            <th>Error Description</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    <%
                                        List<Product> productos = (ArrayList<Product>) request.getAttribute("listaProductos");

                                        for(Product p : productos)
                                        {
                                    %>
                                        <tr>
                                            <td><%= p.getCode() %></td>
                                            <td><%= p.getEan() %></td>
                                            <td><%= p.getBrand() %></td>
                                            <td><%= p.getName() %></td>
                                            <td><%= p.getCategory() %></td>
                                            <td><%= p.getWeight() %></td>
                                            <td><%= p.getOnlineDateTime() %></td>
                                            <td><%= p.getOfflineDateTime() %></td>
                                            <td><%= p.getApprovalStatus() %></td>
                                            <td><%= p.getDescription() %></td>
                                            <td><%= p.getImportOrigin() %></td>
                                            <td><%= p.getProcessed() %></td>
                                            <td><%= p.getErrorDescription() %></td>
                                        </tr>
                                    <%}%>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>



<!-- jQuery -->
<!--<script data-cfasync="false" src="/cdn-cgi/scripts/af2821b0/cloudflare-static/email-decode.min.js"></script>-->
<script src="assets/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="assets/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="assets/gentelella-master/vendors/fastclick/lib/fastclick.js"></script>
<!-- NProgress -->
<script src="assets/gentelella-master/vendors/nprogress/nprogress.js"></script>
<!-- iCheck -->
<script src="assets/gentelella-master/vendors/iCheck/icheck.min.js"></script>
<!-- Datatables -->
<script src="assets/gentelella-master/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
<script src="assets/gentelella-master/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
<script src="assets/gentelella-master/vendors/jszip/dist/jszip.min.js"></script>
<script src="assets/gentelella-master/vendors/pdfmake/build/pdfmake.min.js"></script>
<script src="assets/gentelella-master/vendors/pdfmake/build/vfs_fonts.js"></script>

<!-- Custom Theme Scripts -->
<script src="assets/gentelella-master/build/js/custom.js"></script>
<!-- Google Analytics -->
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-23581568-13', 'auto');
    ga('send', 'pageview');

</script>

</body>
</html>
