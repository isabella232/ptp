#!/usr/bin/perl -w
#*******************************************************************************
#* Copyright (c) 2011 Forschungszentrum Juelich GmbH.
#* All rights reserved. This program and the accompanying materials
#* are made available under the terms of the Eclipse Public License v1.0
#* which accompanies this distribution, and is available at
#* http://www.eclipse.org/legal/epl-v10.html
#*
#* Contributors:
#*    Wolfgang Frings (Forschungszentrum Juelich GmbH) 
#*******************************************************************************/ 
use strict;

sub check_rms {
    my($rmsref,$cmdsref,$verbose)=@_;
    my($key,$cmd);
    my $rc=0;

    my %cmdname=(
		"job"  => "llq",
		"node" => "llstatus",
	);
    my %cmdpath=(
		"job" => "/usr/bin/llq",
		"node" => "/usr/bin/llstatus",
	);
   
    foreach $key (keys(%cmdname)) {
	# check for job query cmd
		if (exists($cmdsref->{"cmd_${key}info"})) {
		    $cmd=$cmdsref->{"cmd_${key}info"};
		} else {
		    $cmd=$cmdpath{$key};
		}
		if (!-f $cmd) {
		    my $cmdpath=`which $cmdname{$key} 2>/dev/null`; 	# last try: which 
		    if (!$?) {
				chomp($cmdpath);
				$cmd=$cmdpath;
				print STDERR "$0: check_rms: found $cmdname{$key} by which ($cmd)\n" if ($verbose);
		    } else {
				last;
		    }
		}
		if (-f $cmd) {
		    $cmdsref->{"cmd_${key}info"}=$cmd;
		    $rc=1;
		}
	}
	
	if ($rc) {
		$$rmsref="LL";
	} else {
		print STDERR "$0: check_rms: seems not to be a LL system\n" if ($verbose);
    }

    return $rc;
}

sub generate_step_rms {
    my($workflowxml, $laststep, $cmdsref)=@_;
    my($step,$envs,$key,$ukey);

    $envs="";
    foreach $key (keys(%{$cmdsref})) {
		$ukey=uc($key);
		$envs.="$ukey=$cmdsref->{$key} ";
    }

    $step="getdata";
    &add_exec_step_to_workflow($workflowxml,$step, $laststep, 
			       "$envs $^X rms/LL/da_system_info_LML.pl               \$tmpdir/sysinfo_LML.xml",
			       "$envs $^X rms/LL/da_nodes_info_LML.pl                \$tmpdir/nodes_LML.xml",
			       "$envs $^X rms/LL/da_jobs_info_LML.pl                 \$tmpdir/jobs_LML.xml");
    $laststep=$step;

    $step="combineLML";
    &add_exec_step_to_workflow($workflowxml,$step, $laststep, 
			       "$^X \$instdir/LML_combiner/LML_combine_obj.pl  -v -o \$stepoutfile ".
			       "\$tmpdir/sysinfo_LML.xml \$tmpdir/jobs_LML.xml \$tmpdir/nodes_LML.xml");
    $laststep=$step;

    return($laststep);
}

1;