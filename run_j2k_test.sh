#run daf using the computed DAGs
echo -ne 'running DAF using the downorder DAGs...'
./daf_2min -d yeast -q yeast_400n -a downorder.dag -n 100 -m 100000 > result_downorder_dag
echo 'done'

echo -ne 'running DAF using the greedyLog DAGs...'
./daf_2min -d yeast -q yeast_400n -a greedyLog.dag -n 100 -m 100000 > result_greedyLog_dag
echo 'done'

echo -ne 'running DAF using the longestPath DAGs...'
./daf_2min -d yeast -q yeast_400n -a downorder.dag -n 100 -m 100000 > result_longestPath_dag
echo 'done'

#run original daf
echo -ne 'running the original DAF...'
./daf_2min -d yeast -q yeast_400n -n 100 -m 100000 > result_daf
echo 'done'

#compare two results
echo '*comparing result of downorder*'
python sort_result.py result_downorder_dag result_daf

#compare two results
echo '*comparing result of greedyLog*'
python sort_result.py result_greedyLog_dag result_daf

#compare two results
echo '*comparing result of longestPath*'
python sort_result.py result_longestPath_dag result_daf

