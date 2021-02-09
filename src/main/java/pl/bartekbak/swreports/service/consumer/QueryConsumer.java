package pl.bartekbak.swreports.service.consumer;

import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;

public interface QueryConsumer {
    Report createReport(Query query);
}
