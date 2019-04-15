# proximity-calculator
CLI for calculating and listing the distances from a location to a set of businesses.

Input files are csvs, with one business entry per line, in the form
    business name, latitude, longitude

# Arguments

     -a,--arguments        Show saved arguments
     -b,--business <arg>   Name of business to which distances will be calculated
     -c,--clear            Clear saved arguments
     -f,--file <arg>       Business .csv file location
                           [REQUIRED]
     -h,--help             Help
     -i,--singleinstance   Run a single instance
     -l,--latlon <arg>     Comma separated lat/lon of business from which distances will be calculated
                           [REQUIRED]
     -o,--order <arg>      Sort order of distance
                           - lg: largest to smallest
                           - sm: smallest to largest
                           [DEFAULT = smallest to largest]
     -s,--save             Save the given arguments so that they do not need to be repeated in subsequent commands
     -u,--unit <arg>       Units of distance
                           - m: metres
                           - km: kilometres
                           - mi: miles
                           [DEFAULT = metre]
     -x,--exit             Exit
 
 # Example input
 
     --file C:/path/to/csv-file.csv --latlon 53.23468,-2.56134 --unit m --order sm --business Fleetsmart
     
 # Example output
 
    Proximity to Fleetsmart
    Eric's Electricals: 17516 m
    Greta's Gaff: 32645 m
    Hannah's Home: 34391 m
    Bob's Builders: 37317 m
    Irene's Imaging: 39272 m
    Dave's Domicile: 49155 m
    Kathy's Kennels: 59092 m
    Claire's Crib: 59093 m
    John's Joint: 71519 m
    Alex's Apartment: 325285 m
