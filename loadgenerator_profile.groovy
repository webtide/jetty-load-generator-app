return new ResourceProfile(new Resource( "index.html",
                                         new Resource( "/css/bootstrap.css",
                                                       new Resource( "/css/bootstrap-theme.css" ),
                                                       new Resource( "/js/jquery-3.1.1.min.js"),
                                                       new Resource( "/js/jquery-3.1.1.min.js"),
                                                       new Resource( "/js/jquery-3.1.1.min.js"),
                                                       new Resource( "/js/jquery-3.1.1.min.js")
                                         ),
                                         new Resource( "/js/bootstrap.js" ,
                                                       new Resource( "/js/bootstrap.js" ),
                                                       new Resource( "/js/bootstrap.js" ),
                                                       new Resource( "/js/bootstrap.js" )
                                         ),
                                         new Resource( "/hello" ),
                                         new Resource( "/hello?name=foo" ),
                                         new Resource( "/hello?name=foo" ),
                                         new Resource( "/upload" ).method("PUT").size(8192),
                                         )
);
