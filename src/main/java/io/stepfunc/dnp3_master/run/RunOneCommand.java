package io.stepfunc.dnp3_master.run;

import io.stepfunc.dnp3.*;
import io.stepfunc.dnp3_master.LoggingFileReader;

import java.time.Duration;
import java.util.List;

import static org.joou.Unsigned.*;
import static org.joou.Unsigned.ubyte;

public class RunOneCommand {

    public static void run(MasterChannel channel, AssociationId association, PollId poll, String command) throws Exception {
        switch (command) {
            case "enable" -> channel.enable();
            case "disable" -> channel.disable();
            case "dln" -> channel.setDecodeLevel(DecodeLevel.nothing());
            case "dlv" -> channel.setDecodeLevel(DecodeLevel.nothing().withApplication(AppDecodeLevel.OBJECT_VALUES));
            case "rao" -> {
                Request request = new Request();
                request.addAllObjectsHeader(Variation.GROUP40_VAR0);
                channel.read(association, request).toCompletableFuture().get();
            }
            case "rmo" -> {
                Request request = new Request();
                request.addAllObjectsHeader(Variation.GROUP10_VAR0);
                request.addAllObjectsHeader(Variation.GROUP40_VAR0);
                channel.read(association, request).toCompletableFuture().get();
            }
            case "cmd" -> {
                // ANCHOR: assoc_control
                CommandSet commands = new CommandSet();
                Group12Var1 control = Group12Var1.fromCode(ControlCode.fromOpType(OpType.LATCH_ON));
                commands.addG12V1U16(ushort(3), control);
                channel
                        .operate(association, CommandMode.SELECT_BEFORE_OPERATE, commands)
                        .toCompletableFuture()
                        .get();
                // ANCHOR_END: assoc_control
            }
            case "evt" -> channel.demandPoll(poll);
            case "lts" -> channel.synchronizeTime(association, TimeSyncMode.LAN).toCompletableFuture().get();
            case "nts" -> channel.synchronizeTime(association, TimeSyncMode.NON_LAN).toCompletableFuture().get();
            case "wad" -> {
                WriteDeadBandRequest request = new WriteDeadBandRequest();
                request.addG34v1U8(ubyte(3),ushort(5));
                request.addG34v3U16(ushort(5), 2.5f);
                channel.writeDeadBands(association, request).toCompletableFuture().get();
            }
            case "fat" -> {
                Request request = new Request();
                request.addTimeAndInterval(ulong(0), uint(86400000));
                request.addAllObjectsHeader(Variation.GROUP20_VAR0);
                channel.sendAndExpectEmptyResponse(association, FunctionCode.FREEZE_AT_TIME, request).toCompletableFuture().get();
            }
            case "rda" -> {
                // ANCHOR: read_attributes
                Request request = new Request();
                request.addSpecificAttribute(AttributeVariations.ALL_ATTRIBUTES_REQUEST, ubyte(0));
                channel.read(association, request).toCompletableFuture().get();
                // ANCHOR_END: read_attributes
            }
            case "wda" -> {
                // ANCHOR: write_attribute
                Request request = new Request();
                request.addStringAttribute(AttributeVariations.USER_ASSIGNED_LOCATION, ubyte(0), "Mt. Olympus");
                channel.sendAndExpectEmptyResponse(association, FunctionCode.WRITE, request).toCompletableFuture().get();
                // ANCHOR_END: write_attribute
            }
            case "ral" -> {
                Request request = new Request();
                request.addSpecificAttribute(AttributeVariations.LIST_OF_VARIATIONS, ubyte(0));
                channel.read(association, request).toCompletableFuture().get();
            }
            case "crt" -> {
                Duration delay = channel.coldRestart(association).toCompletableFuture().get();
                System.out.println("Restart delay: " + delay);
            }
            case "wrt" -> {
                Duration delay = channel.warmRestart(association).toCompletableFuture().get();
                System.out.println("Restart delay: " + delay);
            }
            case "rd" -> {
                // ANCHOR: read_directory
                List<FileInfo> items = channel
                        .readDirectory(association, ".", DirReadConfig.defaults())
                        .toCompletableFuture().get();
                for(FileInfo info : items) {
                    printFileInfo(info);
                }
                // ANCHOR_END: read_directory
            }
            case "gfi" -> {
                // ANCHOR: get_file_info
                FileInfo info = channel.getFileInfo(association, ".").toCompletableFuture().get();
                printFileInfo(info);
                // ANCHOR_END: get_file_info
            }
            case "rf" -> {
                // ANCHOR: read_file
                channel.readFile(association, ".", FileReadConfig.defaults(), new LoggingFileReader());
                // ANCHOR_END: read_file
            }
            case "lsr" -> channel.checkLinkStatus(association).toCompletableFuture().get();
            default -> System.out.println("Unknown command");
        }
    }

    private static void printFileInfo(FileInfo info) {
        System.out.println("file name: " + info.fileName);
        System.out.println("     type: " + info.fileType);
        System.out.println("     size: " + info.size);
        System.out.println("     created: " + info.timeCreated.toString());
    }
}
