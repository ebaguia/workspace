package org.auckland.ac.nz.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class implements the extra functionalities useful by the tool such 
 * running external commands.
 * 
 * @author ebag753
 *
 */
public class VIDIVOXProcessBuilder {

    private String processName;
    
    private String processArguments;
    
    private String processOutput;
    
    private Process process;
    
    /**
     * Initializes the necessary items needed when starting a process.
     * 
     * @param processName
     * @param processArguments
     */
    public VIDIVOXProcessBuilder(String processName, String processArguments) {
        this.processName = processName;
        this.processArguments = processArguments;
    }
    
    /**
     * Retrieves the information of the process while running and after running.
     */
    private void processOutputStream() {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        
        // Let us try to see the standard output of the executed external
        // command
        //
        try {
            String streamLine;
            while((streamLine = bufferReader.readLine()) != null) {
                processOutput += streamLine;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Run extrenal command and async.
     */
    public void startAndDontWait() {
        try {
            process = new ProcessBuilder(processName, processArguments).start();
            processOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Run extrenal command and sync.
     */
    public void startAndWait() {
        try {
            process = new ProcessBuilder(processName, processArguments).start();
            process.wait();
            processOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the standard output of the executed process. Normally, you
     * call this after starting the process, else output is empty.
     * 
     * @return Standard output of the executed process.
     */
    public String getProcessOutput() {
        return processOutput;
    }
    
}
