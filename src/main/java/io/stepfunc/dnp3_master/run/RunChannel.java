package io.stepfunc.dnp3_master.run;

import io.stepfunc.dnp3.AssociationId;
import io.stepfunc.dnp3.MasterChannel;
import io.stepfunc.dnp3.PollId;
import io.stepfunc.dnp3.Request;
import io.stepfunc.dnp3_master.TestAssociationInformation;
import io.stepfunc.dnp3_master.config.TestAssociationConfig;
import io.stepfunc.dnp3_master.handler.TestAssociationHandler;
import io.stepfunc.dnp3_master.handler.TestReadHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;

import static org.joou.Unsigned.ushort;

public class RunChannel {

    public static void run(MasterChannel channel) {

        // Create the association
        // ANCHOR: association_create
        AssociationId association = channel.addAssociation(
                ushort(1024),
                TestAssociationConfig.getAssociationConfig(),
                new TestReadHandler(),
                new TestAssociationHandler(),
                new TestAssociationInformation()
        );
        // ANCHOR_END: association_create

        // Create a periodic poll
        // ANCHOR: add_poll
        PollId poll = channel.addPoll(
                association,
                Request.classRequest(false, true, true, true),
                Duration.ofSeconds(5)
        );
        // ANCHOR_END: add_poll

        // start communications
        System.out.println("===============channel.enable()===============");
        channel.enable();

        // Handle user input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                final String command = reader.readLine();
                if(command.equals("x")) {
                    System.out.println("exiting");
                    return;
                }

                RunOneCommand.run(channel, association, poll, command);
            } catch (Exception e) {
                System.err.println("Error: " + e);
                e.printStackTrace();
            }
        }
    }
}
