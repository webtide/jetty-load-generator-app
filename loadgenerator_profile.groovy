return new ResourceProfile(new Resource( "index.html",
                                         new Resource( "/css/bootstrap.css",
                                                       new Resource( "/css/bootstrap-theme.css" )
                                         ),
                                         new Resource( "/js/bootstrap.js" ,
                                                       new Resource( "/js/bootstrap.js" ),
                                                       new Resource( "/js/bootstrap.js" ),
                                                       new Resource( "/js/bootstrap.js" )
                                         ),
                                         new Resource( "/helloworld" ),
                                         new Resource( "/upload" ).size(4096),
                                         )
);