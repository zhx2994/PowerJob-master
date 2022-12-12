package tech.powerjob.samples.processors.test;/**
 * TODO
 *
 * @author Harrison
 * @date 2022/12/12 10:50
 */

import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

/**
 * @author Harrison
 * @date 2022年12月12日 10:50
 */
public class MyTestProcessorDemo implements BasicProcessor {

    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        // PowerJob 在线日志功能，使用该 Logger 打印的日志可以直接在 PowerJob 控制台查看
        OmsLogger omsLogger = context.getOmsLogger();
        omsLogger.info("测试用例开始执行，上下文： {}.", context);

        return new ProcessResult(true, "任务执行成功~");
    }
}
