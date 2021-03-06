package com.zeryts.c2c.social.govern.report.service.impl;

import com.zeryts.c2c.common.core.enums.ReportTaskVoteEnum;
import com.zeryts.c2c.social.govern.report.dao.ReportTaskVoteDao;
import com.zeryts.c2c.social.govern.report.domain.ReportTaskVote;
import com.zeryts.c2c.social.govern.report.service.ReportTaskVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: Report操作的dao层实现类
 * @title: ReportTaskDaoImpl
 * @projectName: c2c-social-govern
 * @author: zeryts
 * @date: 2020/11/29  10:01
 */
@Service
public class ReportTaskVoteServiceImpl implements ReportTaskVoteService {
    @Autowired
    private ReportTaskVoteDao reportTaskVoteDao;


    @Override
    public void inittVotes(Long reportTaskId , List<Long> reportReviewerIds) {
        for(Long reviewerId : reportReviewerIds) {
            ReportTaskVote reportTaskVote = new ReportTaskVote();
            reportTaskVote.setReviewerId(reviewerId);
            reportTaskVote.setReportTaskId(reportTaskId);
            reportTaskVote.setVoteResult(ReportTaskVoteEnum.UNKNOWN.getKey());
            reportTaskVote.setCreateTime(new Date());
            reportTaskVoteDao.save(reportTaskVote);
        }
    }

    @Override
    public void update(ReportTaskVote vote) {
        reportTaskVoteDao.saveOrUpdate(vote);
    }

    @Override
    public void vote(ReportTaskVote vote) {
        reportTaskVoteDao.updateData(vote);
    }

    @Override
    public Boolean calculateVotes(Long reportTaskId) {
        return null;
    }
}
