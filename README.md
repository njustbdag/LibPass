# LibPass

Third-party library (TPL) detection is an upstream task in the domain of Android application security analysis, and its detection accuracy has a significant impact on its downstream tasks including malware detection, repackaged application detection and privacy leakage detection. To improve accuracy and efficiency, this paper proposes a package structure and signature-based TPL detection approach by leveraging the idea of the pairwise comparison, named LibPass. LibPass is composed of primary module identification, TPL candidate identification and fine-grained detection in a streamed way. The primary module identification aims at improving efficiency by differiate the business code with imported TPL code. The TPL candidate identification leverages the stability of package structure features to deal with obfuscation to improve accuracy, and identify candidate TPLs by comparing package structure signatures to reduce the number of pairwise comparisons. The fine-grained detection accurately discovers the imported TPL of a specific version in a pairwise comparison way on candidate TPLs. To validate the accuracy and the efficiency, three benchmark datasets are built.

#Usage: LibPass accepts two inputs: an Android app (.apk file) and a list of libraries (.dex file). LibRoad returns a list of ARP---library pairs as results.It is possible for each ARP to have more than one lib.Since our approach applies a global searching policy to find a TPL with the highest similarity score as the perfectly matched one instead of relying on a threshold-based policy, which is helpful to minimize the possibility of false positives. TPL Discovery from the Local Repository requires a large Local database, and LibRoad alone does not work.

#1.run apk_lib_groundtruth.sql in the DB folder. 

#2 Connect to the database. 

#3.Open and run .\LibPass\src\cn\fudan\libpecker\main\LibPass.java.
