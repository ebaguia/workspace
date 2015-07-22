package org.auckland.ac.nz.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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
    
    private List<String> command = null;
    
    public VIDIVOXLogger logger = VIDIVOXLogger.getInstance();
    
    /**
     * Initializes the necessary items needed when starting a process.
     * 
     * @param processName
     * @param processArguments
     */
    public VIDIVOXProcessBuilder(List<String> command) {
        this.processName = null;
        this.processArguments = null;
        this.command = command;
        logger.logInfo("VIDIVOXProcessBuilder()::VIDIVOXProcessBuilder() command = " + command);
    }
    
    /**
     * Initializes the necessary items needed when starting a process.
     * 
     * @param processName
     * @param processArguments
     */
    public VIDIVOXProcessBuilder(String processName, String processArguments) {
        this.processName = processName;
        this.processArguments = processArguments;
        this.command = null;
        logger.logInfo("VIDIVOXProcessBuilder()::VIDIVOXProcessBuilder() processName = " + processName);
        logger.logInfo("VIDIVOXProcessBuilder()::VIDIVOXProcessBuilder() processArguments = " + processArguments);
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
                processOutput += "\n" + streamLine;
            }
            
            inputStream.close();
            inputStreamReader.close();
            bufferReader.close();
        } catch (IOException e) {
            logger.logError(e.getMessage());
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
            logger.logError(e.getMessage());
        }
    }
    
    /**
     * Run extrenal command and sync.
     */
    public int startAndWait() {
        int exitCode = 1;
        try {
            ProcessBuilder processBuilder;
            if(command == null) {
                processBuilder = new ProcessBuilder(processName, processArguments);
            }
            else {
                processBuilder = new ProcessBuilder(command);
            }
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            exitCode = process.waitFor();
            processOutputStream();
        } catch (IOException e) {
            logger.logError(e.getMessage());
        } catch (InterruptedException e) {
            logger.logError(e.getMessage());
        }
        
        return exitCode;
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
