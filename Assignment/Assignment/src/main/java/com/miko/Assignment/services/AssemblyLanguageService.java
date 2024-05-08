package com.miko.Assignment.services;

import com.miko.Assignment.model.ProgramState;
import com.miko.Assignment.repository.ProgramStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssemblyLanguageService {

    @Autowired
    private ProgramStateRepository stateRepository;

    public String execute(String[] instructions) {
        ProgramState state = new ProgramState();

        for (String instruction : instructions) {
            String[] query = instruction.split(" ", 2);
            String command = query[0];

            String[] variables = query[1].split(",",2);

            String var1 = variables[0].trim(), var2 = "";
            if(!command.equals("SHOW")){
                var2 = variables[1].trim();
            }

            if (command.equals("MV")) {
                if(var2.charAt(0) == '#'){
                    var2 = var2.substring(1);
                    state.setReg(var1);
                    state.setVal(Integer.parseInt(var2));
                    stateRepository.save(state);
                }
                else{

                    if(stateRepository.findById(var2).isEmpty()) {
                        log.error(var2 + " is not present");
                        break;
                    }
                    int reg2Value = stateRepository.findById(var2).get().getVal();
                    state.setReg(var2);
                    state.setVal(reg2Value);
                    stateRepository.save(state);
                }
            }

            else if(command.equals("ADD")){
                char ch = var2.charAt(0);
                int val = ch - '0';
                if(val >= 0 && val <= 9){
                    state.setReg(var1);
                    state.setVal(stateRepository.findById(var1).get().getVal() + Integer.parseInt(var2));
                    stateRepository.save(state);
                }  else{
                    state.setReg(var1);

                    state.setVal(stateRepository.findById(var1).get().getVal() + stateRepository.findById(var2).get().getVal());
                    stateRepository.save(state);
                }
            }
            else if (command.equals("SHOW")) {
                return String.valueOf(stateRepository.findById(var1).get().getVal());
            }
            else{
                log.error("Invalid Command");
            }

        }
        return "";
    }

}
